package eu.tribusmc.tribuskitpvp.base.ability;

import com.cryptomorin.xseries.messages.ActionBar;
import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.base.CooldownParser;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        TMCPlayer tmcPlayer = base.fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;
        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility().bypassCooldown())
            tmcPlayer.getCurrentKit().getAbility().onPlaceBlock(e);
        else
            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                tmcPlayer.getCurrentKit().getAbility().onPlaceBlock(e);
            });
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getItem() == null) return;
        if (e.getItem().getItemMeta() == null) return;
        Player p = e.getPlayer();

        TMCPlayer tmcPlayer = base.fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;
        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;


        if (tmcPlayer.getCurrentKit().getAbility().bypassCooldown())
            tmcPlayer.getCurrentKit().getAbility().onInteract(e);
        else
            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                tmcPlayer.getCurrentKit().getAbility().onInteract(e);
            });

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

        TMCPlayer tmcPlayer = base.fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;

        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;


        if (tmcPlayer.getCurrentKit().getAbility().bypassCooldown())
            tmcPlayer.getCurrentKit().getAbility().onPlayerDamage(e);
        else
            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                tmcPlayer.getCurrentKit().getAbility().onPlayerDamage(e);
            });

    }


    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {

        if (!(e.getEntity().getShooter() instanceof Player)) return;

        Player p = (Player) e.getEntity().getShooter();

        TMCPlayer tmcPlayer = base.fetchTMCPlayer(p.getName());
        if (tmcPlayer == null) return;


        /*
        if (cooldown.contains(p) && p.getItemInHand().getType() == tmcPlayer.getCurrentKit().getAbility().getHoldingItem().getType()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.i, () -> {
                p.setItemInHand(tmcPlayer.getCurrentKit().getAbility().getHoldingItem());
            }, 1L);
            e.setCancelled(true);
            return;
        }

         */

        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;


        if (tmcPlayer.getCurrentKit().getAbility().bypassCooldown())
            tmcPlayer.getCurrentKit().getAbility().onProjectileLaunch(e);
        else
            cooldownParser.parse(p, tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                tmcPlayer.getCurrentKit().getAbility().onProjectileLaunch(e);
            });


    }


    @EventHandler
    public void onFishingRod(PlayerFishEvent e) {

        TMCPlayer tmcPlayer = base.fetchTMCPlayer(e.getPlayer().getName());
        if (tmcPlayer == null) return;

        if (tmcPlayer.getCurrentKit() == null) return;

        if (tmcPlayer.getCurrentKit().getAbility() == null) return;


        if (tmcPlayer.getCurrentKit().getAbility().bypassCooldown())
            tmcPlayer.getCurrentKit().getAbility().onFishingRoodHook(e);
        else
            cooldownParser.parse(e.getPlayer(), tmcPlayer.getCurrentKit().getAbility().getCooldownTime(), tmcPlayer.getCurrentKit().getAbility().getInternalName(), () -> {
                tmcPlayer.getCurrentKit().getAbility().onFishingRoodHook(e);
            });
    }
}
