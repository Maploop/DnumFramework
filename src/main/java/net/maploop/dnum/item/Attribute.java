package net.maploop.dnum.item;

public enum Attribute {
    STRENGTH("Strength", Integer.MAX_VALUE),
    CRIT_CHANCE("Crit Chance", Integer.MAX_VALUE),
    CRIT_DAMAGE("Crit Damage", Integer.MAX_VALUE),
    HEALTH("Health", Integer.MAX_VALUE),
    DEFENSE("Defense", Integer.MAX_VALUE),
    INTELLIGENCE("Intelligence", Integer.MAX_VALUE);

    private final String name;
    private final int maxValue;

    Attribute(String string, int maxValue) {
        this.name = string;
        this.maxValue = maxValue;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxValue() {
        return this.maxValue;
    }
}
