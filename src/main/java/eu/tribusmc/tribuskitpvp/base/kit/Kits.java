package eu.tribusmc.tribuskitpvp.base.kit;

import eu.tribusmc.tribuskitpvp.base.kit.kits.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Kits {


    private static List<Kit> kits = new ArrayList<>();


    public static void load() {
        kits.add(new Warrior());
        kits.add(new Thor());
        kits.add(new Switcher());
        kits.add(new Grappler());
        kits.add(new Builder());
    }


    public static List<Kit> getUnlockedKits(Player p) {
        return kits.stream().filter(kit -> p.hasPermission(kit.getPermission())).collect(Collectors.toList());
    }

    public static List<Kit> getLockedKits(Player p) {
        return kits.stream().filter(kit -> !p.hasPermission(kit.getPermission())).collect(Collectors.toList());

    }

    public static List<Kit> getKits() {
        return kits;
    }

    public static Kit fetchMatching(String name) {
        return kits.stream().filter(kit -> kit.getName().equals(name)).findFirst().orElse(null);
    }



}
