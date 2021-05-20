package eu.tribusmc.tribuskitpvp.base;

import eu.tribusmc.tribuskitpvp.db.SQLFactory;
import eu.tribusmc.tribuskitpvp.storage.StorageAbstract;

public class TMCPlayers extends StorageAbstract<TMCPlayers> {


    public TMCPlayers(SQLFactory sqlFactory) {
        super(sqlFactory);
    }

    @Override
    public TMCPlayers[] getAll() {
        return new TMCPlayers[0];
    }






}