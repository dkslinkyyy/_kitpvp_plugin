package eu.tribusmc.tribuskitpvp.base.kit.ability;

import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.miscelleanous.CooldownParser;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AbilityImpl implements Listener {

    private final BaseImpl base;
    private final CooldownParser cooldownParser;
    private final String cooldownTickMSG = "§e %internal% Cooldown §8» §6%time%s";
    private final String cooldownFinishedMSG = "§aDu kan nu använda din ability igen";


    public AbilityImpl(BaseImpl base) {
        this.base = base;
        cooldownParser = new CooldownParser();
        cooldownParser.setTickMessage(cooldownTickMSG);
        cooldownParser.setFinishMessage(cooldownFinishedMSG);
    }


    public void clearCooldown(Player p) {
        cooldownParser.clear(p);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        TMCPlayer tmcPlayer = base.getTMCPlayers().fetchByUUID(p.getUniqueId());
        if (tmcPlayer == null) return;
        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;

        if (!isMatching(tmcPlayer.getCurrentKit().getAbility().getListeners(), AbilityListener.BUILD)) return;

        if (!tmcPlayer.getCurrentKit().getAbility().bypassCooldown() && tmcPlayer.getCurrentKit().getAbility().getMainListener() == AbilityListener.BUILD) {

            if (cooldownParser.isParsed(p)) {
                e.setCancelled(true);
                return;
            }

            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                return tmcPlayer.getCurrentKit().getAbility().onPlaceBlock(e, tmcPlayer);
            });
            return;
        }

        tmcPlayer.getCurrentKit().getAbility().onPlaceBlock(e, tmcPlayer);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {


        if (e.getItem() == null) return;
        if (e.getItem().getItemMeta() == null) return;
        Player p = e.getPlayer();

        TMCPlayer tmcPlayer = base.getTMCPlayers().fetchByUUID(p.getUniqueId());
        if (tmcPlayer == null) return;

        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;

        if (!isMatching(tmcPlayer.getCurrentKit().getAbility().getListeners(), AbilityListener.INTERACT)) return;


        if (!tmcPlayer.getCurrentKit().getAbility().bypassCooldown() && tmcPlayer.getCurrentKit().getAbility().getMainListener() == AbilityListener.INTERACT) {

            if (cooldownParser.isParsed(p) && isHoldingItemMatching(p.getItemInHand(), tmcPlayer.getCurrentKit().getAbility().getHoldingItem())) {
                e.setCancelled(true);
                return;
            }

            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                return tmcPlayer.getCurrentKit().getAbility().onInteract(e);
            });
            return;
        }

        tmcPlayer.getCurrentKit().getAbility().onInteract(e);

    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {


        Player p;

        if (e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) e.getDamager();
            p = (Player) projectile.getShooter();
        } else {
            p = (Player) e.getDamager();
        }

        TMCPlayer tmcPlayer = base.getTMCPlayers().fetchByUUID(p.getUniqueId());
        if (tmcPlayer == null) return;

        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;

        if (!isMatching(tmcPlayer.getCurrentKit().getAbility().getListeners(), AbilityListener.PLAYER_DAMAGE)) return;

        if (!tmcPlayer.getCurrentKit().getAbility().bypassCooldown() && tmcPlayer.getCurrentKit().getAbility().getMainListener() == AbilityListener.PLAYER_DAMAGE) {

            if (cooldownParser.isParsed(p)) {
                e.setCancelled(true);
                return;
            }

            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                return tmcPlayer.getCurrentKit().getAbility().onPlayerDamage(e);
            });
            return;
        }

        tmcPlayer.getCurrentKit().getAbility().onPlayerDamage(e);

    }


    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) return;

        Player p = (Player) e.getEntity().getShooter();

        TMCPlayer tmcPlayer = base.getTMCPlayers().fetchByUUID(p.getUniqueId());
        if (tmcPlayer == null) return;

        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;


        if (!isMatching(tmcPlayer.getCurrentKit().getAbility().getListeners(), AbilityListener.PROJECTILE_LAUNCH))
            return;

        if(!isHoldingItemMatching(p.getItemInHand(), tmcPlayer.getCurrentKit().getAbility().getHoldingItem())) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.i, () -> {
            p.getInventory().setItem(p.getInventory().getHeldItemSlot(), tmcPlayer.getCurrentKit().getAbility().getHoldingItem());
        }, 1L);

        if (!tmcPlayer.getCurrentKit().getAbility().bypassCooldown() && tmcPlayer.getCurrentKit().getAbility().getMainListener() == AbilityListener.PROJECTILE_LAUNCH) {

            if (cooldownParser.isParsed(p)) {
                e.setCancelled(true);
                return;
            }

            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                return tmcPlayer.getCurrentKit().getAbility().onProjectileLaunch(e);
            });
            return;
        }

        tmcPlayer.getCurrentKit().getAbility().onProjectileLaunch(e);


    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) return;

        Player p = (Player) e.getEntity().getShooter();

        TMCPlayer tmcPlayer = base.getTMCPlayers().fetchByUUID(p.getUniqueId());
        if (tmcPlayer == null) return;

        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;


        if (!isMatching(tmcPlayer.getCurrentKit().getAbility().getListeners(), AbilityListener.PROJECTILE_LAUNCH))
            return;

        if (!isHoldingItemMatching(p.getItemInHand(), tmcPlayer.getCurrentKit().getAbility().getHoldingItem())) return;


        if (!tmcPlayer.getCurrentKit().getAbility().bypassCooldown() && tmcPlayer.getCurrentKit().getAbility().getMainListener() == AbilityListener.PROJECTILE_LAUNCH) {

            if (cooldownParser.isParsed(p)) {
                return;
            }

            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                return tmcPlayer.getCurrentKit().getAbility().onProjectileHit(e);
            });
            return;
        }

        tmcPlayer.getCurrentKit().getAbility().onProjectileHit(e);


    }


    @EventHandler
    public void onFishingRod(PlayerFishEvent e) {


        TMCPlayer tmcPlayer = base.getTMCPlayers().fetchByUUID(e.getPlayer().getUniqueId());
        if (tmcPlayer == null) return;

        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;

        if (!isMatching(tmcPlayer.getCurrentKit().getAbility().getListeners(), AbilityListener.FISHING_ROD)) return;

        if (!tmcPlayer.getCurrentKit().getAbility().bypassCooldown() && tmcPlayer.getCurrentKit().getAbility().getMainListener() == AbilityListener.FISHING_ROD) {

            if (cooldownParser.isParsed(e.getPlayer())) {
                return;
            }

            cooldownParser.parse(e.getPlayer(), tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                return tmcPlayer.getCurrentKit().getAbility().onFishingRoodHook(e);
            });
            return;
        }

        tmcPlayer.getCurrentKit().getAbility().onFishingRoodHook(e);


    }

    public boolean isHoldingItemMatching(ItemStack item, ItemStack comparable) {

        if (item.getType() != comparable.getType()) return false;
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().hasDisplayName()) return false;
        if (!item.getItemMeta().getDisplayName().equals(comparable.getItemMeta().getDisplayName())) return false;
        return true;
    }


    public boolean isMatching(AbilityListener[] listeners, AbilityListener listener) {
        return Arrays.stream(listeners).filter(l -> l == listener).findFirst().orElse(null) != null;
    }
}
