package eu.tribusmc.tribuskitpvp.base.kits.abilities;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public interface IAbility {

    String getInternalName();

    ItemStack getHoldingItem();

    int getHoldingSlot();

    void onPlayerDamage();

    Outcome onFishingRoodHook(PlayerFishEvent e);

    void onPlaceBlock();

    void onBreakBlock();

    void onInteract();

    void onClick();



}
