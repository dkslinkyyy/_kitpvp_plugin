package eu.tribusmc.tribuskitpvp.base.kits.abilities;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.inventory.ItemStack;

public interface IAbility {

    String getInternalName();

    ItemStack getHoldingItem();

    int getHoldingSlot();

    void onPlayerDamage();

    void onFishingRoodHook();

    void onPlaceBlock();

    void onBreakBlock();

    void onInteract();

    void onClick();



}
