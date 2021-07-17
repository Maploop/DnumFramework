package net.maploop.dnum.npc.npcs;

import net.maploop.dnum.npc.NPC;
import net.maploop.dnum.npc.NPCParameters;

@NPCParameters(
        idname = "npc_example",
        name = "&eMaploop!",
        id = 1,
        signature = "hZ6Eousc7rHVh/b+BKACLykXTteU6oJYed5j3aayFvN9fA34RURrHDdyDZf9s0uZmJ98jgLgZMM5TaE+XY1Xw1LV3qfNv2EYJBslL/3jnWUe9arnAl2NUzaRqSdFyKG1Mu/c/OjtIDGDBsIGLlJSjQijvG76+Xl5qaJwLtkFTm1z2Gqu7Lk21Y1t0xyvA4UDRG0BKxU5yPDNxQkQBfcfVK6lCPXCqk3pHGeZQihn8csiioyiIamCQC1cFOZvIyfgCBQqqqSzIzPciHBHfSz8kaWFOXuT/yFHJPmtqcBzd0eu2mF0pmwcy5h/uI5LJ41YzyY5xyEKP8JvnK3L0LBs//963sM9IAzBBdMTZmKvDEX0AVhp2U2asER3hv3yTECfNexR4TUUvQv7O7rQg3/pRSCOeI7oSp0AHvPUM2vVqaPFdYe8mVDPw4g7fxiIxWcWll3IQMjTXr6EV31ohlrAgFvj0v/3KEGDfzn4nZQB9DBo16wFW13EoS4nkRPoNORAQhkfCwzu0q12PWv/A0fKKPyzKsr5Zo+YSOTCa65HCzIqN4LV790rRxPgZOStkC4+kaMzoS/abUsATf8YxJox+OO/xgIuklcQ7jOATRjpmwTXJB0KB2FnO8ROcFDDjpZNwyOeHDu4WyeMA3kS2/2O4jpsU5EASF5vnJF0/7Ti2Cs=",
        texture = "ewogICJ0aW1lc3RhbXAiIDogMTYyNjUxNDk1OTMwMCwKICAicHJvZmlsZUlkIiA6ICIyMjI3ZDM4ZmRkYTA0OGJmODA1MTFlNzlmNTBlOWVhYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJKdXN0TWFwIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2FjNjlmYmMxOGQxNjkwZGYwZmE2MDM4OTFiMDA1Y2NhZWZmMzkxYTRlNDBkOTgwYTgwZGM0MjQ2MTg4MjQ0MDYiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==",

        world = "world",
        x = -10,
        y = 70,
        z = -73,

        looking = true
)
public class ExampleNPC extends NPC {
    @Override
    public void onClick(PlayerClickNPCEvent event) {
        event.getPlayer().sendMessage("§aThanks for using Dnum framework! §d<3");
    }
}
