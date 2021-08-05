package eu.tribusmc.tribuskitpvp.commands;

import eu.tribusmc.tribuskitpvp.commands.sub.*;
import org.bukkit.command.CommandSender;

public class KitPvPCommand extends CommandManager{

    public KitPvPCommand() {
        super("tribuskitpvp", "tribusmc.admin", false);

        super.registerSubCommand(new AddSpawnpointCommand());
        super.registerSubCommand(new SetSpawnCommand());
        super.registerSubCommand(new CarePackagePointCommand());
        super.registerSubCommand(new AddKitCrateLocationCommand());
        super.registerSubCommand(new CoinCommand());
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {

    }
}
