package eu.tribusmc.tribuskitpvp.commands.sub;

import eu.tribusmc.tribuskitpvp.Core;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddSpawnpointCommand extends SubCommand{

    public AddSpawnpointCommand() {
        super("tribuskitpvp", "addpoint", "tribusmc.admin", "");
    }

    @Override
    public void executeSub(CommandSender s, String[] args) {

        Player p = (Player) s;
        List<Location> points = null;
        if(Core.i.getConfig().getList("points") != null) {
            points = cast(Core.i.getConfig().getList("points"));
        }else{
            points = new ArrayList<>();
        }
        points.add(p.getLocation());

        Core.i.getConfig().set("points", points);
        Core.i.saveConfig();
        p.sendMessage("testing1");

    }

    @SuppressWarnings("unchecked")
    public static <T extends List<?>> T cast(Object obj) {
        return (T) obj;
    }
}
