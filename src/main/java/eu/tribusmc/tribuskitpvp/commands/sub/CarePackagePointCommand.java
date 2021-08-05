package eu.tribusmc.tribuskitpvp.commands.sub;

import eu.tribusmc.tribuskitpvp.Core;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CarePackagePointCommand extends SubCommand{
    public CarePackagePointCommand() {
        super("tribuskitpvp", "cratepoint", "tribus.admin", "");
    }

    @Override
    public void executeSub(CommandSender s, String[] args) {

        List<Location> points;

        if(Core.i.getConfig().getList("cratepoints") == null) {
            points = new ArrayList<>();
        }else {
            points = Core.cast(Core.i.getConfig().getList("cratepoints"));
        }

        Player p = (Player) s;

        points.add(p.getLocation().getBlock().getLocation());

        Core.i.getConfig().set("cratepoints", points);
        Core.i.saveConfig();

    }
}
