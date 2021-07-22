package net.maploop.dnum.v_1_16_R3_npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import net.maploop.dnum.Dnum;
import net.maploop.dnum.util.Messaging;
import net.maploop.dnum.util.Util;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class NPC {
	public static final List<NPC> NPC_LIST = new ArrayList<>();
	private static final List<EntityPlayer> ENTITY_PLAYER_REGISTERY = new ArrayList<>();

	public static final Map<String, NPC> NPC_REGISTERY = new HashMap<>();
	public static final Map<String, List<String>> NPC_VIEW = new HashMap<>();

	private static final Dnum plugin = Dnum.getInstance();

	private GameProfile profile;
	private UUID uuid;

	@Getter
	private int entityId;

	@Getter
	private final String name, idname;
	private final String world;
	@Getter
	private final double x, y, z;
	private final float yaw, pitch;

	@Getter
	private static EntityPlayer entityPlayer;

	@Getter
	private final Location location;

	protected String texture = "";
	protected String signature = "";

	protected String skinName = "$none";

	public NPC(String idname, String name, String world, double x, double y, double z, float yaw, float pitch) {
		this.idname = idname;
		this.name = Util.colorize(name);
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;

		this.location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	public void create() {
		MinecraftServer server = ((CraftServer) plugin.getServer()).getServer();
		WorldServer worldServer = ((CraftWorld) Bukkit.getWorld(world)).getHandle();

		UUID uuid = UUID.randomUUID();
		this.uuid = uuid;

		profile = new GameProfile(uuid, this.name);

		if (!this.skinName.equals("$none")) {
			profile.getProperties().put("textures", new Property("textures", NPCUtil.getSkin(this.skinName)[0], NPCUtil.getSkin(this.skinName)[1]));
		} else {
			profile.getProperties().put("textures", new Property("textures", texture, signature));
		}

		EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, profile, new PlayerInteractManager(worldServer));
		entityPlayer.setLocation(x, y, z, yaw, pitch);

		this.entityPlayer = entityPlayer;
		this.entityId = entityPlayer.getId();
	}

	public void spawn(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		sendInfo(player);
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.entityPlayer));
		connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.entityPlayer, (byte) (entityPlayer.yaw * 256 / 360)));

		Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> removeInfo(player), 80);
	}

	public void despawn(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		removeInfo(player);
		connection.sendPacket(new PacketPlayOutEntityDestroy(this.entityId));
	}

	private void sendInfo(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.entityPlayer));
	}

	private void removeInfo(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.entityPlayer));
	}

	public void register() {
		NPC_LIST.add(this);
		NPC_REGISTERY.put(this.idname, this);
	}

	public abstract void interact(NPCClickEvent event);

	public static final class NPCDespawnPreventer {
		public void start() {
			Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {

				Bukkit.getOnlinePlayers().forEach(player -> {
					for (NPC npc : NPC_LIST) {
						List<String> viewing = NPC_VIEW.get(player.getName());

						if (npc.getLocation().distance(player.getLocation()) >= 60) {
							npc.despawn(player);
							viewing.remove(npc.getIdname());

							NPC_VIEW.put(player.getName(), viewing);
						} else {
							if (viewing.contains(npc.getIdname())) continue;

							Messaging.debug("[", player.getName(), "] <= [", npc.getIdname(), "] (NPC LOG)");
							Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> npc.spawn(player), 5);

							viewing.add(npc.getIdname());
							NPC_VIEW.put(player.getName(), viewing);
						}
					}
				});

			}, 10, 20);
		}
	}
}
