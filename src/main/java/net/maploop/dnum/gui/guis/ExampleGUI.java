package net.maploop.dnum.gui.guis;

import net.maploop.dnum.gui.GUI;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ExampleGUI extends GUI {
    public ExampleGUI(net.maploop.dnum.gui.PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getTitle() {
        return "Example";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void hadleMenu(InventoryClickEvent event) {

    }

    @Override
    public void setItems() {
        Player
    }
}
