package net.maploop.dnum.listener;

import net.maploop.dnum.Dnum;
import net.maploop.dnum.gui.PlayerMenuUtility;
import net.maploop.dnum.gui.guis.ItemsGUI;
import net.maploop.dnum.util.signgui.SignGUIUpdateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class SignGUIUpdate implements Listener {
    @EventHandler
    public void onUpdate(SignGUIUpdateEvent event) {
        Player player = event.getPlayer();
        if(ItemsGUI.searching.contains(player)) {
            ItemsGUI.search.put(player, event.getSignText()[0]);

            new BukkitRunnable() {
                @Override
                public void run() {
                    new ItemsGUI(new PlayerMenuUtility(player)).open();
                    ItemsGUI.searching.remove(player);
                }
            }.runTaskLater(Dnum.getInstance(), 2);
            return;
        }
    }
}
