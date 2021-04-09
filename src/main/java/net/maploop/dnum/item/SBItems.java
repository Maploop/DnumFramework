package net.maploop.dnum.item;

import net.maploop.dnum.Dnum;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SBItems {
    public static String uniqueId;
    public static Map<UUID, String> id = new HashMap<>();
    public static Map<UUID, String> cooldownmn = new HashMap<>();

    public static Map<String, CustomItem> items = new HashMap<>();
    public static Map<Integer, String> itemIDs = new HashMap<>();

    public static Set<ItemStack> categoryArmor = new HashSet<>();

    public static void putItem(String name, CustomItem item) {
        items.put(name, item);
        itemIDs.put(item.getID(), name);

        switch (item.getType()) {
            case HELMET:
            case CHESPLATE:
            case LEGGINGS:
            case BOOTS: {
                categoryArmor.add(CustomItem.fromString(Dnum.getInstance(), item.getName(), 1));
            }
        }
    }

    public static List<ItemStack> getItems() {
        List<ItemStack> gottenItems = new ArrayList<>();

        for(String key : items.keySet()) {
            ItemStack item = CustomItem.fromString(Dnum.getInstance(), key, 1);
            gottenItems.add(item);
        }
        gottenItems.remove(CustomItem.fromString(Dnum.getInstance(), "skyblock_menu", 1));

        return gottenItems;
    }

}
