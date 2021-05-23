package eu.tribusmc.tribuskitpvp.base.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.kits.abilities.IAbility;
import org.bukkit.Material;


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
