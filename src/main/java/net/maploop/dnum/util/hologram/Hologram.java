package net.maploop.dnum.util.hologram;

import net.maploop.dnum.util.Util;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public final class Hologram {
    public static final Map<UUID, List<Integer>> VIEWING_HOLOGRAMS = new HashMap<>();

    private final IChatBaseComponent text;
    private final Location loc;
    private final boolean toAll;
    private final Player player;

    public Hologram(String text, Player player, Location loc, boolean toAll) {
        this.text = new ChatMessage(Util.colorize(text));
        this.loc = loc;
        this.toAll = toAll;
        this.player = player;
    }

    public EntityArmorStand send() {
        EntityArmorStand stand = new EntityArmorStand(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
        stand.setSmall(true);
        stand.setCustomNameVisible(true);
        stand.setCustomName(text);
        stand.setInvisible(true);
        stand.setNoGravity(true);

        if(toAll) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                if (putStand(stand, player)) return stand;
            }
        } else {
            if (putStand(stand, player)) return stand;
        }

        return stand;
    }

    public void update(EntityArmorStand stand) {
        stand.setCustomName(text);

        Runnable runnable = () -> {
            putStand(stand, player);
        };

        if (this.toAll) {
            despawn(player, stand.getId());
            // i dont fucking know dude
        }
    }

    public void unSend(int id) {
        PacketPlayOutEntityDestroy packetDestroy = new PacketPlayOutEntityDestroy(id);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetDestroy);
    }

    private boolean putStand(EntityArmorStand stand, Player player) {
        if(VIEWING_HOLOGRAMS.containsKey(player.getUniqueId())) {
            List<Integer> list = new ArrayList<>(VIEWING_HOLOGRAMS.get(player.getUniqueId()));
            list.add(stand.getId());

            VIEWING_HOLOGRAMS.put(player.getUniqueId(), list);

            sendPackets(player, stand);
            return true;
        }
        VIEWING_HOLOGRAMS.put(player.getUniqueId(), Collections.singletonList(stand.getId()));
        sendPackets(player, stand);
        return false;
    }

    public void sendPackets(Player player, EntityArmorStand stand) {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    private void despawn(Player player, int id) {
        PacketPlayOutEntityDestroy despawnPacket = new PacketPlayOutEntityDestroy(id);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(despawnPacket);
    }
}
