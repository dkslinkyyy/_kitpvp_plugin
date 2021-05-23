package eu.tribusmc.tribuskitpvp.base.player;

import eu.tribusmc.tribuskitpvp.base.effects.DeathEffect;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TMCPlayer  {

    private final UUID uuid;
    private final String playerName;
    private final int kills, deaths;
    private Kit kit;
    private DeathEffect deathEffect;

    public TMCPlayer(UUID uuid, String playerName, int kills, int deaths) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.kills = kills;
        this.deaths = deaths;
    }


    public void setCurrentKit(Kit kit) {
        this.kit = kit;
    }

    public Kit getCurrentKit() {
        return kit;
    }


    public void setDeathEffect(DeathEffect deathEffect) {
        this.deathEffect = deathEffect;
    }

    public DeathEffect getDeathEffect() {
        return deathEffect;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

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
