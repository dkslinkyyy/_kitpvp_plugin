package eu.tribusmc.tribuskitpvp.commands;

import eu.tribusmc.tribuskitpvp.commands.sub.AddSpawnpointCommand;
import eu.tribusmc.tribuskitpvp.commands.sub.SetSpawnCommand;
import org.bukkit.command.CommandSender;

public class KitPvPCommand extends CommandManager{

    public KitPvPCommand() {
        super("kitpvp", "tribusmc.admin", false);

        super.registerSubCommand(new AddSpawnpointCommand());
        super.registerSubCommand(new SetSpawnCommand());
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {

    }
}
