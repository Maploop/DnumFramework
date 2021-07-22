package net.maploop.dnum.listener;

import com.comphenix.protocol.injector.packet.PacketInjector;
import net.maploop.dnum.Dnum;
import net.maploop.dnum.util.hologram.DHologram;
import net.maploop.dnum.util.scoreboard.PlayerScoreboard;
import net.maploop.dnum.v_1_16_R3_npc.NPC;
import net.maploop.dnum.v_1_16_R3_npc.PacketReader;
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

import java.util.ArrayList;
import java.util.List;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        new PacketReader(player).inject();

        new PlayerScoreboard("&a&lGAMING", player, DisplaySlot.SIDEBAR, "%%space%%", "we be gamin'", "%%space%%", "gaming.net").sendScoreboard(true);
        new DHologram("&cthis should be only shown to " + player.getName(), new Location(Bukkit.getWorld("world"), -49, 67, -132)).send(player);

        List<String> viewing = new ArrayList<>();

        /*
        for (NPC npc : NPC.NPC_LIST) {
            npc.spawn(player);

            if (!(npc.getLocation().distance(player.getLocation()) > 60)) {
                viewing.add(npc.getIdname());
            }
        }

        NPC.NPC_VIEW.put(player.getName(), viewing);
         */
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {

    }
}
