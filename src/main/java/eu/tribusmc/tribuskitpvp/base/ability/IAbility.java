package eu.tribusmc.tribuskitpvp.base.ability;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public interface IAbility {

    String getInternalName();

    ItemStack getHoldingItem();

    boolean bypassCooldown();

    int getCooldownTime();

    int getHoldingSlot();

    boolean onPlayerDamage(EntityDamageByEntityEvent e);

    boolean onFishingRoodHook(PlayerFishEvent e);

    boolean onPlaceBlock(BlockPlaceEvent e);

    boolean onBreakBlock(BlockBreakEvent e);

    boolean onInteract(PlayerInteractEvent e);

    boolean onProjectileLaunch(ProjectileLaunchEvent e);

    boolean onProjectileHit(ProjectileHitEvent e);

    boolean onFlightToggle(PlayerToggleFlightEvent e);

}
