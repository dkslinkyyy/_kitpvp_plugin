package eu.tribusmc.tribuskitpvp;

import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.base.TopThree;
import eu.tribusmc.tribuskitpvp.base.crate.CoinCrate;
import eu.tribusmc.tribuskitpvp.base.crate.KitCrate;
import eu.tribusmc.tribuskitpvp.commands.KitPvPCommand;
import eu.tribusmc.tribuskitpvp.gui.GUIManager;
import eu.tribusmc.tribuskitpvp.gui.PlayerGUI;
import eu.tribusmc.tribuskitpvp.placeholders.PlaceholderCollector;
import eu.tribusmc.tribuskitpvp.placeholders.TribusKitPvPPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Core extends JavaPlugin {

    public static Core i;

    private PlaceholderCollector placeholderCollector;
    public BaseImpl base;
    private List<KitCrate> kitCrates;
    private GUIManager guiManager;

    @Override
    public void onEnable() {
        i = this;

        new KitPvPCommand();

        placeholderCollector = new PlaceholderCollector();
        try {
            placeholderCollector.register("tribuskitpvp");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        base = new BaseImpl();
        guiManager = new GUIManager(this, base);
        guiManager.loadAll();

        kitCrates = new ArrayList<>();

        new TribusKitPvPPlaceholder(this).register();

        if (getConfig().getList("kitcratepoints") != null) {
            getConfig().getList("kitcratepoints").forEach(o -> {
                kitCrates.add(new KitCrate(base, (Location) o));
            });
        }
        kitCrates.forEach(kitCrate -> {
            kitCrate.create();
        });

    }

    @Override
    public void onDisable() {
        kitCrates.forEach(kitCrate -> {
            kitCrate.despawnRollingHologram();
            kitCrate.despawnIdleHologram();
        });
        base.getTMCPlayers().save();
        base = null;
    }

    public String trans(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    @SuppressWarnings("unchecked")
    public static <T extends List<?>> T cast(Object obj) {
        return (T) obj;
    }

    public PlaceholderCollector getPlaceholderCollector() {
        return placeholderCollector;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

}
