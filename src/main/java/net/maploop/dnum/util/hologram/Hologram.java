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
    private static final List<Hologram> HOLOGRAMS = new ArrayList<>();

    public static final Map<UUID, HashMap<Integer, EntityArmorStand>> ARMOR_STAND_MAP = new HashMap<>();

    public static List<Hologram> getHolograms() {
        return HOLOGRAMS;
    }

    public static Hologram getHologram(int index) {
        return HOLOGRAMS.get(index);
    }

    private final double x, y, z, id;
    private final String text, world;
    private final Player player;

    private static EntityArmorStand armorStand;

    public Hologram(String text, Player player, String world, double x, double y, double z, int id) {
        this.text = Util.colorize(text);
        this.world = world;
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

    public EntityArmorStand send() {
        EntityArmorStand stand = new EntityArmorStand(((CraftWorld) Bukkit.getWorld(this.world)).getHandle(), this.x, this.y, this.z);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setMarker(true);
        stand.setNoGravity(true);

        stand.setCustomNameVisible(true);
        stand.setCustomName(new ChatComponentText(text));

        armorStand = stand;

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving(armorStand));

        return armorStand;
    }
}
