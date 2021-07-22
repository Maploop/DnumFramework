package net.maploop.dnum.v_1_16_R3_npc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.maploop.dnum.Dnum;
import net.minecraft.server.v1_16_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public final class PacketReader extends Reflections {
	private final Player player;
	private Channel channel;

	public PacketReader(Player player) {
		this.player = player;
	}

	public void inject() {
		CraftPlayer cplayer = (CraftPlayer) this.player;
		channel = cplayer.getHandle().playerConnection.networkManager.channel;
		channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
			@Override
			protected void decode(ChannelHandlerContext arg0, Packet<?> packet, List<Object> arg2) throws Exception {
				arg2.add(packet);
				readPacket(packet);
			}
		});
	}

	public void uninject() {
		if (channel.pipeline().get("PacketInjector") != null) {
			channel.pipeline().remove("PacketInjector");
		}
	}

	private void readPacket(Packet<?> packet) {
		if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
			int id = (int) getValue(packet, "a");

			if (getValue(packet, "d").equals("OFF_HAND")) return;
			if (getValue(packet, "action").equals("INTERACT_AT")) return;

			if (getValue(packet, "action").equals("INTERACT") || getValue(packet, "action").equals("ATTACK")) {
				for (NPC npc : NPC.NPC_LIST) {
					if (npc.getEntityId() == id) {
						Bukkit.getScheduler().scheduleAsyncDelayedTask(Dnum.getInstance(), () -> npc.interact(new NPCClickEvent(player, npc)), 3);
					}
				}
			}
		}
	}
}
