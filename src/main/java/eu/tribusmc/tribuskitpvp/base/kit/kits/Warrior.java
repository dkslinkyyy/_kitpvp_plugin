package eu.tribusmc.tribuskitpvp.base.kit.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kit.ability.IAbility;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;


public class Warrior extends Kit {


    public Warrior() {
        super("Warrior", null);
    }

    @Override
    public int getSlot() {
        return 11;
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
        return XMaterial.GOLDEN_SWORD;
    }

    @Override
    public IAbility getAbility() {
        return null;
    }
}
