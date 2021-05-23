package eu.tribusmc.tribuskitpvp.commands.sub;



import eu.tribusmc.tribuskitpvp.commands.CommandManager;
import org.bukkit.command.CommandSender;


import java.util.stream.Collectors;

public abstract class SubCommand {


    private final String subCommand, usage;
    private final CommandManager parentCommand;
    private final boolean reqArgs;

    private final String permission;

    public SubCommand(String parentCommandName, String subCommand, String permission, String usage) {
        this.subCommand = subCommand;
        this.parentCommand = CommandManager.getCommands().stream().filter(cmd -> cmd.getCommand().equalsIgnoreCase(parentCommandName)).collect(Collectors.toList()).get(0);
        this.usage = usage;
        this.reqArgs = !usage.isEmpty();
        this.permission = permission;

    }

    public String getPermission() {
        return permission;
    }

    public CommandManager getParentCommand() {
        return parentCommand;
    }

    public boolean isRequiringArgs() {
        return reqArgs;
    }


    public String getUsage() {
        return usage;
    }

    public String getSubCommand() {
        return subCommand;
    }

    public abstract void executeSub(CommandSender s, String[] args);
}
