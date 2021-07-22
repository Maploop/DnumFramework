package net.maploop.dnum.v_1_16_R3_npc;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class NPCClickEvent extends Event {
	public static final HandlerList HANDLER_LIST = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	@Getter
	private final NPC npc;
	@Getter
	private final Player player;

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

	public NPCClickEvent(Player player, NPC npc) {
		this.player = player;
		this.npc = npc;
	}

	public enum Click {
		ATTACK,
		OFF_HAND,
		INTERACT_AT,
		INTERACT;
	}
}
