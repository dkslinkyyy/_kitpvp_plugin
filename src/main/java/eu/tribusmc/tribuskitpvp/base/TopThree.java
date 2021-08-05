package eu.tribusmc.tribuskitpvp.base;


import com.cryptomorin.xseries.SkullUtils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayers;
import eu.tribusmc.tribuskitpvp.miscelleanous.Skull;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class TopThree {

    private int mode = 0;
    private List<TMCPlayer> tmcPlayers;
    private Location loc;
    private String hologramTitle = "§6§lFlest Mord";
    private UUID currentUUID;
    private Hologram hologram;
    private boolean openChest = false;

    public TopThree(TMCPlayers tmcPlayers) {
        this.tmcPlayers = tmcPlayers.getAll();
    }

    public void create() {
        loc = new Location(Bukkit.getWorld("KitPvP"), 8.518f, 149, 0.531f);

        createHologram();

        startTimer();
    }

    private void createHologram() {

        Location hologramLocation = new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getBlockY() + 2.5, loc.getBlockZ() + 0.5);

        hologram = HologramsAPI.createHologram(Core.i, hologramLocation);
        hologram.appendTextLine(hologramTitle);
        assert currentUUID != null;
        hologram.appendItemLine(Skull.retrieve(currentUUID));
        //hologram.appendTextLine(hologramFooter);
    }

    public void removeHolograms() {

    }

    public TMCPlayer getPlayerByPlace(int place) {
        if (place > 2) return null;
        return tmcPlayers.get(place);
    }


    public void startTimer() {
        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 10, 20);
        timer.whenFinished(time -> {
            System.out.println("refreshing..");
            refresh();
        }).resetAtFinish(true).run(true);
    }

    public void refresh() {

        switchMode();

    }


    public void updateHolograms() {

    }


    public void sort(int mode) {
        switch (mode) {
            case 1:
                Collections.sort(tmcPlayers, Comparator.comparing(tmcPlayer -> tmcPlayer.getCoins()));
                break;
            case 2:
                Collections.sort(tmcPlayers, Comparator.comparing(tmcPlayer -> tmcPlayer.getKills()));
                break;
            case 3:
                Collections.sort(tmcPlayers, Comparator.comparing(tmcPlayer -> tmcPlayer.getDeaths()));
                break;
        }
    }

    public void switchToCurrenText(int mode){
        switch (mode) {
            case 1:
                hologramTitle = "§6§lFlest Mord";
                break;
            case 2:
                hologramTitle = "§6§lFlest Dödsfall";
                break;
            case 3:
                hologramTitle = "§6§lFlest Mynt";
                break;
        }
    }

    public void switchMode() {
        if (mode == 3) {
            mode = 1;
            return;
        }
        mode++;
        sort(mode);
        switchToCurrenText(mode);
    }

    public void destroy() {

    }
}
