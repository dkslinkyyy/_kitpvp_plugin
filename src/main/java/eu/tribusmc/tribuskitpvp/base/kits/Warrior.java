package eu.tribusmc.tribuskitpvp.base.kits;

import eu.tribusmc.tribuskitpvp.base.kits.abilities.GraplingHook;
import eu.tribusmc.tribuskitpvp.base.kits.abilities.IAbility;


public class Warrior implements IKit{



    @Override
    public IAbility getAbility() {
        return new GraplingHook();
    }

    @Override
    public void equip() {

    }
}
