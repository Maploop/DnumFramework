package net.maploop.dnum.command;

import net.maploop.dnum.Dnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {
    private final Dnum plugin;
    private final String commandName;
    private final String permission;
    private final boolean canConsoleUse;
    private String description = "";
    private String usage = "/<command>";
    private final String[] aliases;

    public AbstractCommand(String commandName, String permission, String description, String usage, boolean canConsoleUse, String... aliases) {
        this.commandName = commandName;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        this.aliases = aliases;
        this.plugin = Dnum.getInstance();

        if(description != null)
            this.description = description;
        if(usage != null)
            this.usage = usage.replace("<command>", commandName);

        plugin.getCommand(commandName).setExecutor(this);
        plugin.getCommand(commandName).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!cmd.getLabel().equalsIgnoreCase(commandName))
            return true;
        if(permission != null) {
            if(!sender.hasPermission(permission)){
                sender.sendMessage("§cYou must be admin or higher to use this command!");
                return true;
            }
        }
        if(!canConsoleUse && !(sender instanceof Player)){
            sender.sendMessage("§cOnly players are allowed to execute this command.");
            return true;
        }
        cmd.setDescription(description);
        cmd.setAliases(Arrays.asList(aliases));

        execute(sender, args);
        return true;
    }

    public abstract void execute(CommandSender sender, String[] args);
}
