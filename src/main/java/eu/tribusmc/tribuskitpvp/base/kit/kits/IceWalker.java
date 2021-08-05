package eu.tribusmc.tribuskitpvp.base.kit.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;

public class IceWalker extends Kit {


    public IceWalker() {
        super("Ice Walker", null);
    }

    @Override
    public int getSlot() {
        return 0;
    }

    @Override
    public String[] getLore() {
        return new String[0];
    }

    @Override
    public XMaterial getHoldingItem() {
        return XMaterial.BLUE_ICE;
    }


}
