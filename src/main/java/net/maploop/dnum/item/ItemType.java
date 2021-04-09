package net.maploop.dnum.item;

public enum ItemType {
    SWORD("SWORD"),
    BOW("BOW"),
    BOOTS("BOOTS"),
    LEGGINGS("LEGGINGS"),
    CHESPLATE("CHESTPLATE"),
    HELMET("HELMET"),
    ITEM(""),
    WAND("WAND"),
    ACCESSORY("ACCESSORY"),
    DUNGEON_ITEM("DUNGEON ITEM"),
    DUNGEON_BOW("DUNGEON BOW"),
    DUNGEON_SWORD("DUNGEON SWORD"),
    AXE("AXE"),
    PICKAXE("PICKAXE"),
    HOE("HOE"),
    UNOBTAINABLE_ITEM("UNOBTAINABLE ITEM");

    private String s;

    ItemType(String s) {
        this.s = s;
    }

    public String getValue() {
        return s;
    }
}
