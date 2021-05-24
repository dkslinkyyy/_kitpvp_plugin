package eu.tribusmc.tribuskitpvp.base.kit;

import java.util.ArrayList;
import java.util.List;

public class KitManager {


    public List<Kit> kits;

    public KitManager() {
        kits = new ArrayList<>();
    }



    public void addKit(Kit kit) {
        kits.add(kit);
    }

    public void removeKit(Kit kit) {

    }



}
