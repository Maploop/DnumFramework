package net.maploop.dnum;

import net.maploop.dnum.command.commands.Command_example;
import net.maploop.dnum.item.ItemType;
import net.maploop.dnum.item.Rarity;
import net.maploop.dnum.item.SBItems;
import net.maploop.dnum.item.items.TemplateItem;
import net.maploop.dnum.listener.InventoryClick;
import net.maploop.dnum.listener.ItemManager;
import net.maploop.dnum.listener.SignGUIUpdate;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dnum extends JavaPlugin {
    private static Dnum dnum;

    @Override
    public void onEnable() {
        dnum = this;
        loadCommands();
        loadItems();
        loadListeners();

        this.getServer().getConsoleSender().sendMessage("§aPlugin was enabled!\n§ePowered by the DnumFramework made by Maploop");
    }

    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage("§cPlugin was disabled.");
    }

    public static Dnum getInstance() { return dnum; }

    private void loadCommands() {
        new Command_example();
    }
    private void loadListeners() {
        PluginManager m = this.getServer().getPluginManager();
        m.registerEvents(new InventoryClick(), this);
        m.registerEvents(new ItemManager(this), this);
        m.registerEvents(new SignGUIUpdate(), this);
    }
    private void loadItems() {
        SBItems.putItem("template_item", new TemplateItem(0, Rarity.COMMON, "Template", Material.STONE, 0, false, false, false, null, 0, false, ItemType.ITEM, false, false, 1, 1,1 ,1, 1,1));
    }
}
