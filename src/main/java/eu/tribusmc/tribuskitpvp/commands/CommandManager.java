package eu.tribusmc.tribuskitpvp.commands;


import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.commands.sub.SubCommand;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandManager implements CommandExecutor {

    private final String permission;
    private final String cmd;
    private final boolean canConsoleUse;
    private static final List<CommandManager> commandsList = new ArrayList<>();


    public CommandManager(String cmd, String permission, boolean canConsoleUse) {
        this.cmd = cmd;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        commandsList.add(this);
        Core.i.getCommand(cmd).setExecutor(this);
    }


    public static List<CommandManager> getCommands() {
        return commandsList;
    }

    public String getCommand() {
        return cmd;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player) && !(canConsoleUse)) {
            System.out.println("You can't do this!");
            return false;
        }

        if(!sender.hasPermission(permission) && !permission.isEmpty()) {
            sender.sendMessage(Core.i.trans("&cNo permission to do that!"));
            return false;
        }

        if(args.length == 0) {

            execute(sender, args);

        }else{

            if(args[0].equalsIgnoreCase("help")) {
                if(getSubCommands().size() == 0) return true;
                sender.sendMessage(Core.i.trans("&8&m------------------"));
                getSubCommands().forEach(sb -> {

                    String cmdName = sb.getParentCommand().getCommand();
                    String subName = sb.getSubCommand();
                    String usage = sb.getUsage();

                    if(!sb.isRequiringArgs()) {
                        sender.sendMessage(Core.i.trans("&7/" + cmdName + " &b" + subName));

                    }else{
                        sender.sendMessage(Core.i.trans("&7/" + cmdName + " &b" + subName + " &7" + usage));

                    }

                });
                sender.sendMessage(Core.i.trans("&8&m------------------"));

                return true;
            }

            SubCommand foundSubCommand = fetchSubCommand(args[0]);

            if(foundSubCommand == null) {
                sender.sendMessage(Core.i.trans("&cWrong usage! /" + this.cmd + " help"));
                return false;
            }


            if(!sender.hasPermission(foundSubCommand.getPermission()) && !permission.isEmpty()) {
                sender.sendMessage(Core.i.trans("&cNo permission to do that!"));
                return false;
            }

            if(foundSubCommand.isRequiringArgs() && ArrayUtils.remove(args,0).length == 0) {
                sender.sendMessage(Core.i.trans("&cWrong usage! /" + foundSubCommand.getParentCommand().getCommand() + " " + foundSubCommand.getSubCommand()
                        + " " + foundSubCommand.getUsage()));
                return false;
            }

            foundSubCommand.executeSub(sender, (String[]) ArrayUtils.remove(args, 0));

        }

        return false;

    }

    private SubCommand fetchSubCommand(String subCMD) {
        return subCommands.stream().filter(self -> self.getSubCommand().equalsIgnoreCase(subCMD)).findFirst().orElse(null);
    }

    private final List<SubCommand> subCommands = new ArrayList<>();

    protected List<SubCommand> getSubCommands() {
        return subCommands;
    }

    protected void registerSubCommand(SubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    protected abstract void execute(CommandSender sender, String[] args);

}

