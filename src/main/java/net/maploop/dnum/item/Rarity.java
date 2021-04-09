package net.maploop.dnum.item;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    MYTHIC(ChatColor.LIGHT_PURPLE),
    SPECIAL(ChatColor.RED),
    VERY_SPECIAL(ChatColor.RED),
    UNFINISHED(ChatColor.DARK_RED),
    SKYBLOCK_MENU(ChatColor.YELLOW),
    UNOBTAINABLE(ChatColor.DARK_AQUA);

    private ChatColor color;

    Rarity(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public boolean isRarerThan(Rarity rarity) {
        int current = getIndex();
        int param = rarity.getIndex();
        return (current > param);
    }

    public int getIndex() {
        int index = 0;
        for (Rarity rarity : values()) {
            if (equals(rarity))
                return index;
            index++;
        }
        return -1;
    }
}
