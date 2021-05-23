package eu.tribusmc.tribuskitpvp.base.kit.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.ability.IAbility;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;

public class Bomber extends Kit {

    public Bomber() {
        super("Bomber", null);
    }

    @Override
    public int getSlot() {
        return 20;
    }

    @Override
    public String[] getLore() {
        return new String[0];
    }

    @Override
    public XMaterial getHoldingItem() {
        return XMaterial.TNT;
    }
}
