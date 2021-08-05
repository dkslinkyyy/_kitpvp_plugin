package eu.tribusmc.tribuskitpvp.base.player;

import eu.tribusmc.tribuskitpvp.base.effects.DeathEffect;
import eu.tribusmc.tribuskitpvp.base.effects.DeathEffects;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import eu.tribusmc.tribuskitpvp.gui.GUI;
import eu.tribusmc.tribuskitpvp.gui.KitGUI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.*;

public class TMCPlayer extends SimpleKitPlayer implements Cloneable, ConfigurationSerializable {

    private final UUID uuid;
    private final String playerName;
    private Kit kit, latestKit;
    private DeathEffect deathEffect;

    public TMCPlayer(UUID uuid, String playerName, int kills, int deaths, int coins) {
        super(kills, deaths, coins);
        this.uuid = uuid;
        this.playerName = playerName;

        deathEffect = DeathEffects.fetchMatching("BURN");

    }


    public void setCurrentKit(Kit kit) {
        this.kit = kit;
    }

    public Kit getCurrentKit() {
        return kit;
    }

    public void setLatestKit(Kit latestKit) {
        this.latestKit = latestKit;
    }

    public Kit getLatestKit() {
        return latestKit;
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


    public static TMCPlayer deserialize(Map<String, Object> deserializableMap) {


        String playerUUID = (String) deserializableMap.get("UUID");
        String playerName = (String) deserializableMap.get("playerName");
        int kills = (int) deserializableMap.get("kills");
        int deaths = (int) deserializableMap.get("deaths");
        int coins = (int) deserializableMap.get("coins");

        return new TMCPlayer(UUID.fromString(playerUUID),playerName,kills,deaths,coins);
    }



    public List<GUI> getPersonalGUIs()
    {


        return null;
    }



    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();

        serialized.put("UUID", uuid.toString());
        serialized.put("playerName", playerName);
        serialized.put("deathEffect", getDeathEffect().getName());
        if(kit != null) {
            serialized.put("kit", kit.getName());
        }
        serialized.put("kills", super.getKills());
        serialized.put("deaths", super.getDeaths());
        serialized.put("coins", super.getCoins());

        return serialized;
    }


}
