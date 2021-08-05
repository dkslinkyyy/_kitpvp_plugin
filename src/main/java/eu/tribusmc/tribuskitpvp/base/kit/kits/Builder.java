package eu.tribusmc.tribuskitpvp.base.kit.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kit.ability.abilities.BuilderAbility;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;

public class Builder extends Kit {

    public Builder() {
        super("Builder", new BuilderAbility());
    }

    @Override
    public int getSlot() {
        return 14;
    }

    @Override
    public String[] getLore() {
        return new String[0];
    }

    @Override
    public XMaterial getHoldingItem() {
        return XMaterial.BRICK;
    }
}
