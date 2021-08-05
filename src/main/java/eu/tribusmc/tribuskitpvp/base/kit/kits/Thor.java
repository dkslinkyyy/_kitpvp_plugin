package eu.tribusmc.tribuskitpvp.base.kit.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kit.ability.abilities.MjolnirAbility;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;

public class Thor extends Kit {

    public Thor() {
        super("Thor", new MjolnirAbility());
    }

    @Override
    public int getSlot() {
        return 15;
    }

    @Override
    public String[] getLore() {
        return new String[0];
    }

    @Override
    public XMaterial getHoldingItem() {
        return XMaterial.IRON_AXE;
    }
}
