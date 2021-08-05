package eu.tribusmc.tribuskitpvp.base.kit.ability.abilities;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.kit.ability.AbilityListener;
import eu.tribusmc.tribuskitpvp.base.kit.ability.IAbility;
import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;
import eu.tribusmc.tribuskitpvp.gui.GUIItem;
import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
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

public class GrapplingHookAbility implements IAbility {


    @Override
    public String getInternalName() {
        return "Grappling Hook";
    }

    @Override
    public ItemStack getHoldingItem() {
        return new GUIItem("hook", XMaterial.FISHING_ROD).setTitle("§a§lGrappling Hook").glow().hideAttributes().getOutcome();
    }

    @Override
    public boolean bypassCooldown() {
        return false;
    }




    @Override
    public AbilityListener[] getListeners() {
        return new AbilityListener[] {AbilityListener.FISHING_ROD};
    }

    @Override
    public AbilityListener getMainListener() {
        return AbilityListener.FISHING_ROD;
    }

    @Override
    public int getCooldownTime() {
        return 30;
    }

    @Override
    public int getHoldingSlot() {
        return 1;
    }

    @Override
    public boolean onPlayerDamage(EntityDamageByEntityEvent e) {
        return false;
    }

    @Override
    public boolean onFishingRoodHook(PlayerFishEvent e) {

        if (e.getHook().getLocation().subtract(0, 1, 0).getBlock().getType().isSolid() || e.getHook().getLocation().getBlock().getType().isSolid()) {

            Particle.send(e.getPlayer(), EnumParticle.CLOUD, new Float[]{0.3f, 0.3f, 0.3f}, 0f, 23, true);

            Bukkit.getOnlinePlayers().forEach(oP -> {
                oP.playSound(e.getHook().getLocation(), Sound.ITEM_BREAK, 1.5f, 1f);
            });

            pullEntityToLocation(e.getPlayer(), e.getHook().getLocation());

            return true;

        }


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

    private void pullEntityToLocation(final Entity e, Location loc) {
        Location entityLoc = e.getLocation();

        entityLoc.setY(entityLoc.getY() + 0.5);
        e.teleport(entityLoc);

        double t = loc.distance(entityLoc);
        double v_x = (1.0 + 0.20 * t) * (loc.getX() - entityLoc.getX()) / t;
        double v_y = (1.0 + 0.26 * t) * (loc.getY() - entityLoc.getY()) / t;
        double v_z = (1.0 + 0.20 * t) * (loc.getZ() - entityLoc.getZ()) / t;

        Vector v = e.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        e.setVelocity(v);

    }


}
