package eu.tribusmc.tribuskitpvp.base.kit.ability.abilities;

import eu.tribusmc.tribuskitpvp.base.kit.ability.AbilityListener;
import eu.tribusmc.tribuskitpvp.base.kit.ability.IAbility;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public class IceWalkerAbility implements IAbility {


    @Override
    public String getInternalName() {
        return null;
    }

    @Override
    public ItemStack getHoldingItem() {
        return null;
    }

    @Override
    public boolean bypassCooldown() {
        return false;
    }

    @Override
    public AbilityListener[] getListeners() {
        return new AbilityListener[0];
    }

    @Override
    public AbilityListener getMainListener() {
        return null;
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }

    @Override
    public int getHoldingSlot() {
        return 0;
    }

    @Override
    public boolean onPlayerDamage(EntityDamageByEntityEvent e) {
        return false;
    }

    @Override
    public boolean onFishingRoodHook(PlayerFishEvent e) {
        return false;
    }

    @Override
    public boolean onPlaceBlock(BlockPlaceEvent e, TMCPlayer player) {
        return false;
    }

    @Override
    public boolean onBreakBlock(BlockBreakEvent e) {
        return false;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e) {
        return false;
    }



    @Override
    public boolean onProjectileLaunch(ProjectileLaunchEvent e) {
        return false;
    }

    @Override
    public boolean onProjectileHit(ProjectileHitEvent e) {
        return false;
    }

    @Override
    public boolean onFlightToggle(PlayerToggleFlightEvent e) {
        return false;
    }
}
