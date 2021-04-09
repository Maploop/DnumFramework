package net.maploop.dnum.item;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemAbility {
    private String name;
    private AbilityType type;
    private String description = "This item wasn't given a description!";
    private int cooldown = 0;

    public ItemAbility(String name, AbilityType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public ItemAbility(String name, AbilityType type, String description, int cooldown) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.cooldown = cooldown;
    }

    public List<String> toLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Item Ability: " + this.name + " " + ChatColor.YELLOW + ChatColor.BOLD + this.type.getText());
        List<String> desc = Arrays.asList(this.description.split("\n"));
        for (String item : desc) {
            item = ChatColor.GRAY + item;
            lore.add(item);
        }
        if (this.cooldown > 0)
            lore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + this.cooldown + "s");
        return lore;
    }
}
