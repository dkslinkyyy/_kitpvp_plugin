package eu.tribusmc.tribuskitpvp.base.kit.ability.abilities;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kit.ability.AbilityListener;
import eu.tribusmc.tribuskitpvp.base.kit.ability.IAbility;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.gui.GUIItem;
import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MjolnirAbility implements IAbility {

    List<Player> inAir = new ArrayList<>();

    @Override
    public String getInternalName() {
        return "Mjölnir";
    }

    @Override
    public ItemStack getHoldingItem() {
        return new GUIItem("Mjölnir", XMaterial.IRON_AXE).setTitle("§c§lMjölnir").glow().hideAttributes().getOutcome();
    }

    @Override
    public boolean bypassCooldown() {
        return false;
    }

    @Override
    public AbilityListener[] getListeners() {
        return new AbilityListener[] { AbilityListener.INTERACT, AbilityListener.PLAYER_DAMAGE};
    }

    @Override
    public AbilityListener getMainListener() {
        return AbilityListener.INTERACT;
    }

    @Override
    public int getCooldownTime() {
        return 45;
    }

    @Override
    public int getHoldingSlot() {
        return 1;
    }

    @Override
    public boolean onPlayerDamage(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player)) return false;
        if (!(e.getEntity() instanceof Player)) return false;

        Player damaged = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();

        if (damager.getItemInHand().getType() != getHoldingItem().getType()) return false;

        if (!inAir.contains(damaged)) {
            e.setCancelled(true);
            return false;
        }

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

        Player p = e.getPlayer();

        if (e.getItem().getType() != getHoldingItem().getType()) return false;
        if (p.getNearbyEntities(8, 8, 8).size() < 1) return false;

        Bukkit.getOnlinePlayers().forEach(oP -> {
            oP.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1.5f, 1f);
        });

        for (Entity entity : p.getNearbyEntities(8, 8, 8)) {

            if (!(entity instanceof Player)) continue;

            Player damaged = (Player) entity;

            if (damaged.getGameMode() != GameMode.SURVIVAL) continue;

            inAir.add(damaged);
            Particle.send(p, EnumParticle.CLOUD, new Float[]{0.3f, 0.1f, 0.3f}, 0, 17, true);
            damaged.setVelocity(new Vector(0, 1.5, 0));

            Timer t1 = new Timer(Timer.TimerType.DELAY, 1, 20);

            t1.execute(time -> {
                damaged.getWorld().strikeLightningEffect(entity.getLocation());
                damaged.damage(18, p);
                damaged.setVelocity(new Vector(0, -5, 0));
                inAir.remove(damaged);
            }).run(true);

        }

        return true;

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

