package eu.tribusmc.tribuskitpvp.base;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TMCPlayer  {

    private final UUID uuid;
    private final String playerName;
    private final int kills, deaths;

    public TMCPlayer(UUID uuid, String playerName, int kills, int deaths) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.kills = kills;
        this.deaths = deaths;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
    1
    public String getPlayerName() {
        return playerName;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKills() {
        return kills;
    }
}
