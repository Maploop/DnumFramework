package net.maploop.dnum;

import net.maploop.dnum.command.CommandLoader;
import net.maploop.dnum.listener.InventoryClick;
import net.maploop.dnum.listener.PlayerJoin;
import net.maploop.dnum.listener.SignGUIUpdate;
import net.maploop.dnum.util.DLog;
import net.maploop.dnum.util.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
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

    @Override
    public void onEnable() {
        dnum = this;
        cl = new CommandLoader();
        try{
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            this.commandMap = (CommandMap) f.get(Bukkit.getServer());
        }catch (Exception e) {
            e.printStackTrace();
        }

        DLog.info("Loading commands...");
        loadCommands();
        DLog.info("Loading listeners...");
        loadListeners();

        DLog.info("Plugin was enabled!");
    }

    @Override
    public void onDisable() {
        DLog.info("Plugin was disabled.");
    }

    public static Dnum getInstance() { return dnum; }

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
