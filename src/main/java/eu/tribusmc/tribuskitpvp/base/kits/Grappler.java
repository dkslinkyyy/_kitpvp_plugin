package eu.tribusmc.tribuskitpvp.base.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kits.abilities.GrapplingHook;

public class Grappler extends Kit{

    public Grappler() {
        super("Grappler", new GrapplingHook());
    }

    @Override
    public int getSlot() {
        return 11;
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
