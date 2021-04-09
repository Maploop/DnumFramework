package net.maploop.dnum.command.commands;

import net.maploop.dnum.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Command_example extends AbstractCommand {
    public Command_example() {
        super("example", "dnum.example.use", "Just a simple example command!", "/<command>", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("Example command executed!");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
