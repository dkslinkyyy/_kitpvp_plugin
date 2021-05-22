package eu.tribusmc.tribuskitpvp.commands.sub;

import eu.tribusmc.tribuskitpvp.Core;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends SubCommand{

    public SetSpawnCommand() {
        super("kitpvp", "setspwn", "tribusmc.admi", "");
    }

    @Override
    public void executeSub(CommandSender s, String[] args) {
        Player p = (Player) s;

        Core.i.getConfig().set("lobby", p.getLocation());
        Core.i.saveConfig();
        p.sendMessage("testing2");

    }
}
