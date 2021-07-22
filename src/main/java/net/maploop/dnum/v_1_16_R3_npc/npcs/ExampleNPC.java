package net.maploop.dnum.v_1_16_R3_npc.npcs;

import net.maploop.dnum.v_1_16_R3_npc.NPC;
import net.maploop.dnum.v_1_16_R3_npc.NPCClickEvent;
import org.bukkit.entity.Player;

public class ExampleNPC extends NPC {
	public ExampleNPC() {
		super("npc_example", "&2Maploop", "world", 10, 70, -73, 0, 20);
	}

	@Override
	public void interact(NPCClickEvent event) {
		Player player = event.getPlayer();
		player.sendMessage("Thanks for using dnum framework v1.16!");
	}
}
