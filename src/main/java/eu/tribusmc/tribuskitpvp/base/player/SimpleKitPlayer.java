package eu.tribusmc.tribuskitpvp.base.player;

import com.avaje.ebean.validation.NotNull;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import org.bukkit.entity.Player;

public class SimpleKitPlayer {

    private int deaths, kills, coins;
    private Kit kit, latestKit;

    public SimpleKitPlayer(int paramKills, int paramDeaths, int paramCoins) {
        this.kills = paramKills;
        this.deaths = paramDeaths;
        this.coins = paramCoins;
    }




    public void kill(Player player) {
        //
    }


    @NotNull
    public void setKills(int paramKills) {
        this.kills = paramKills;
    }


    @NotNull
    public void addKills(int paramKills) {
        this.kills += paramKills;
    }

    @NotNull
    public int getKills() {
        return kills;
    }

    @NotNull
    public void setDeaths(int paramDeaths) {
        this.deaths = paramDeaths;
    }

    @NotNull
    public void addDeaths(int paramDeaths) {
        this.deaths += paramDeaths;
    }

    @NotNull
    public int getDeaths() {
        return deaths;
    }


    @NotNull
    public void setCoins(int paramCoins) {
        this.coins = paramCoins;
    }

    @NotNull
    public void addCoins(int paramCoins) {
        this.coins += paramCoins;
    }

    @NotNull
    public void takeCoins(int paramCoins) {
        this.coins -= paramCoins;
    }


    @NotNull
    public int getCoins() {
        return coins;
    }

}
