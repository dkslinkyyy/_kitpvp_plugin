package eu.tribusmc.tribuskitpvp.base.kit.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import eu.tribusmc.tribuskitpvp.base.kit.ability.abilities.GrapplingHookAbility;

public class Grappler extends Kit {

    public Grappler() {
        super("Grappler", new GrapplingHookAbility());
    }

    @Override
    public int getSlot() {
        return 12;
    }

    @Override
    public String[] getLore() {
        return new String[]{
                "",
                "§7Ta dig fram snabbare med en",
                "§eGrappling Hook§7.",
                "",
        };
    }

    @Override
    public XMaterial getHoldingItem() {
        return XMaterial.FISHING_ROD;
    }


}
