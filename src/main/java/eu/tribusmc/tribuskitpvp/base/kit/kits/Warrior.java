package eu.tribusmc.tribuskitpvp.base.kit.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.ability.IAbility;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;


public class Warrior extends Kit {


    public Warrior() {
        super("Warrior", null);
    }

    @Override
    public int getSlot() {
        return 10;
    }

    @Override
    public String[] getLore() {
        return new String[]{
                "",
                "§7Ett basic PvP kit.",
                "",
        };
    }

    @Override
    public XMaterial getHoldingItem() {
        return XMaterial.IRON_HELMET;
    }

    @Override
    public IAbility getAbility() {
        return null;
    }
}
