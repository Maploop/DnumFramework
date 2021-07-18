package net.maploop.dnum.listener;

import com.comphenix.protocol.injector.packet.PacketInjector;
import net.maploop.dnum.Dnum;
import net.maploop.dnum.npc.NPC;
import net.maploop.dnum.npc.NPCRegistery;
import net.maploop.dnum.npc.PacketReader;
import net.maploop.dnum.util.hologram.Hologram;
import net.maploop.dnum.util.scoreboard.PlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        new PacketReader(player).inject();

        NPCRegistery.rotationTaskMap.put(player.getUniqueId(), Dnum.getInstance().startRotating(player));

        new PlayerScoreboard("&a&lGAMING", player, DisplaySlot.SIDEBAR, "%%space%%", "we be gamin'", "%%space%%", "gaming.net").sendScoreboard(true);

        for(NPC npc : NPC.getNpcs()) {
            npc.spawn(player);
            if(npc.getLocation().distance(player.getLocation()) > 100) {
                NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), false);
            } else {
                NPCRegistery.idfk.put(player.getName() + "_" + npc.getParameters().idname(), true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        for (NPC npc : NPC.getNpcs()) {
            NPCRegistery.idfk.remove(event.getPlayer().getName() + "_" + npc.getParameters().idname());
            npc.despawn(event.getPlayer());
        }

        new PacketReader(event.getPlayer()).uninject();

        NPCRegistery.rotationTaskMap.get(event.getPlayer().getUniqueId()).cancel();

        NPCRegistery.rotationTaskMap.remove(event.getPlayer().getUniqueId());
    }
}
