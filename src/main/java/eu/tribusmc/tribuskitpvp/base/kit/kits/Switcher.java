package eu.tribusmc.tribuskitpvp.base.kit.kits;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kit.Kit;
import eu.tribusmc.tribuskitpvp.base.ability.abilities.SwitchPearl;

public class Switcher extends Kit {

    public Switcher() {
        super("Switcher", new SwitchPearl());
    }

    @Override
    public int getSlot() {
        return 13;
    }

    @Override
    public String[] getLore() {
        return new String[]{
                "",
                "§7Kasta §eSwitch-Pearls §7på spelare",
                "§7för att byta plats med dem.",
                "",
        };
    }

    @Override
    public XMaterial getHoldingItem() {
        return XMaterial.ENDER_EYE;
    }
}
