package eu.tribusmc.tribuskitpvp.base.player;

import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.config.ConfigLoader;
import eu.tribusmc.tribuskitpvp.config.configs.DataFile;


import java.util.*;

public class TMCPlayers {

    private Core core;
    private ConfigLoader dataConf;
    private List<TMCPlayer> tmcPlayers;

    public TMCPlayers(Core core) {
        this.core = core;
        tmcPlayers = new ArrayList<>();

    }

    public void loadPlayers() {
        if (dataConf.getCustomConfig().getConfigurationSection("tmcPlayers") != null) {
            for(String key : dataConf.getCustomConfig().getConfigurationSection("tmcPlayers").getKeys(false)) {
                tmcPlayers.add((TMCPlayer) dataConf.getCustomConfig().get("tmcPlayers."+key));
            }
        }
    }

    public void add(TMCPlayer tmcPlayer) {
        if(contains(tmcPlayer)) return;
        tmcPlayers.add(tmcPlayer);
    }

    public void remove(TMCPlayer tmcPlayer) {
        if(!contains(tmcPlayer)) return;
        tmcPlayers.remove(tmcPlayer);
    }


    public void save(TMCPlayer tmcPlayer) {
        dataConf.getCustomConfig().set("tmcPlayers." + tmcPlayer.getUUID().toString(), tmcPlayer);
        dataConf.saveConfig();
    }



    public void save() {

        if(!tmcPlayers.isEmpty()) {
            tmcPlayers.forEach(tmcP -> {
                dataConf.getCustomConfig().set("tmcPlayers." + tmcP.getUUID().toString(), tmcP);

            });
        }
        dataConf.saveConfig();
    }





    public void refresh(TMCPlayer tmcPlayer) {
        tmcPlayers.remove(tmcPlayer);
        tmcPlayers.add(tmcPlayer);
    }


    public boolean contains(TMCPlayer tmcPlayer) {
        return tmcPlayers.stream().filter(self -> self.getUUID().equals(tmcPlayer.getUUID())).findFirst().orElse(null) != null;
    }
    public boolean contains(UUID uuid) {

        return tmcPlayers.stream().filter(self -> self.getUUID().equals(uuid)).findFirst().orElse(null) != null;
    }

    public TMCPlayer fetchByUUID(UUID uuid) {
        return tmcPlayers.stream().filter(self -> self.getUUID().equals(uuid)).findFirst().orElse(null);

    }




    public List<TMCPlayer> getAll() {
        return tmcPlayers;
    }


    public void prepareDataConfig() {
        dataConf = new DataFile(core);
        dataConf.reloadCustomConfig();
    }


    @SuppressWarnings("unchecked")
    private <T extends List<TMCPlayer>> T cast(Object obj) {
        return (T) obj;
    }


}