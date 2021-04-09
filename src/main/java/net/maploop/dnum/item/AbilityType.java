package net.maploop.dnum.item;

public enum AbilityType {
    LEFT_CLICK("LEFT CLICK"),
    RIGHT_CLICK("RIGHT CLICK"),
    MIDDLE_CLICK("MIDDLE CLICK"),
    NONE("");

    private String text;

    AbilityType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
