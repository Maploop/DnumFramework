package net.maploop.dnum.command.commands;

import net.maploop.dnum.command.AbstractCommand;
import net.maploop.dnum.command.CommandParameters;
import net.maploop.dnum.command.CommandSource;
import net.maploop.dnum.gui.guis.ExampleGUI;
import net.maploop.dnum.util.signgui.SignGUI;

@CommandParameters(
        usage = "/<command> <args>",
        permission = "dnum.example",
        description = "Example command that opens the ExampleGUI!",
        inGameOnly = true,
        aliases = {"e", "ex"}
)
// Commands are automatically registered in the CommadMap. You don't need to put the commands in your plugin.yml.
// You need to name your classes like "Command_<name>" unless it won't work. To change this, go to the AbstractCommand class
// And set the COMMAND_PREFIX to whatever you want. Or make it a suffix!

public class Command_example extends AbstractCommand {
    @Override
    public void run(CommandSource sender, String[] args) {
        send("§aHello! This is an §eexample§a.");

        // This is how you open the SignGUI.
        if(true) {
            new SignGUI(sender.getPlayer(), new String[] {"^^^^^", "Enter a", "query!"}, (input -> {
                send("&a" + input);
                // Send the player's input to themselves,
            }));
            return;
        }

        // This is how you open menus to a player:
        new ExampleGUI().open(sender.getPlayer());
    }
}
