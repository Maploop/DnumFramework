package net.maploop.dnum;

import net.maploop.dnum.command.CommandLoader;
import net.maploop.dnum.listener.InventoryClick;
import net.maploop.dnum.listener.PlayerJoin;
import net.maploop.dnum.listener.SignGUIUpdate;
import net.maploop.dnum.npc.NPC;
import net.maploop.dnum.npc.NPCRegistery;
import net.maploop.dnum.npc.npcs.ExampleNPC;
import net.maploop.dnum.util.DLog;
import net.maploop.dnum.util.scoreboard.GlobalScoreboard;
import net.maploop.dnum.util.scoreboard.PlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;

import java.lang.reflect.Field;

/**
 * Dnum framework, a simple framework with a lot of useful utilities!
 * Made for those who are lazy.
 * This framework includes: An easy to use command framework,
 * An easy to use GUI framework, and useful things in utilities!
 *
 * @author Maploop
 */
public final class Dnum extends JavaPlugin {
	private static Dnum dnum;
	public CommandMap commandMap;
	public CommandLoader cl;
	NPCRegistery nr;

	@Override
	public void onEnable() {
		dnum = this;
		cl = new CommandLoader();
		nr = new NPCRegistery();
		try {
			Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			f.setAccessible(true);
			this.commandMap = (CommandMap) f.get(Bukkit.getServer());
		} catch (Exception e) {
			e.printStackTrace();
		}

		DLog.info("Loading commands...");
		loadCommands();
		loadNpcs();
		DLog.info("Loading listeners...");
		loadListeners();

        DLog.info("Registering NPCs...");
        loadNpcs();
		new NPC.DespawnPreventer().start();

		DLog.info("scoreboard is going!!!");

		DLog.info("Plugin was enabled!");
	}

	public BukkitTask startRotating(Player player) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				for (NPC npc : NPC.getNpcs()) {
					if (!npc.getParameters().looking()) continue;

					if (player.getWorld() != Bukkit.getWorld("world")) return;
					if (npc.getLocation().distance(player.getLocation()) < 20)
						npc.rotateHeadtoPlayer(player);
				}
			}
		}.runTaskTimer(this, 0, 1);
	}

	public void loadNpcs() {
		nr.register(new ExampleNPC());
	}

	@Override
	public void onDisable() {
		DLog.info("Plugin was disabled.");
	}

	public static Dnum getInstance() {
		return dnum;
	}

	private void loadCommands() {
		cl.register(new net.maploop.dnum.command.commands.Command_example());
	}

	private void loadListeners() {
		PluginManager m = this.getServer().getPluginManager();
		m.registerEvents(new InventoryClick(), this);
		m.registerEvents(new SignGUIUpdate(), this);
		m.registerEvents(new PlayerJoin(), this);
	}

}
