package eu.tribusmc.tribuskitpvp.base;

import com.avaje.ebean.validation.NotNull;

import java.util.UUID;

public class TMCPlayer  {

    private final UUID uuid;


    public TMCPlayer(UUID uuid) {
        this.uuid = uuid;
    }








    public boolean hasEquipedKit() {
        return false;
    }




}
