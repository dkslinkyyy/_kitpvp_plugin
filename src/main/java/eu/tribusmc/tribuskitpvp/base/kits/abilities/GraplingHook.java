package eu.tribusmc.tribuskitpvp.base.kits.abilities;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;

public class GraplingHook implements IAbility {


    @Override
    public String getInternalName() {
        return "GraplingHook";
    }

    @Override
    public ItemStack getHoldingItem() {
        return XMaterial.FISHING_ROD.parseItem();
    }

    @Override
    public int getHoldingSlot() {
        return 1;
    }

    @Override
    public void onPlayerDamage() {
    }

    @Override
    public void onFishingRoodHook() {

    }

    @Override
    public void onPlaceBlock() {

    }

    @Override
    public void onBreakBlock() {

    }

    @Override
    public void onInteract() {

    }

    @Override
    public void onClick() {

    }
}
