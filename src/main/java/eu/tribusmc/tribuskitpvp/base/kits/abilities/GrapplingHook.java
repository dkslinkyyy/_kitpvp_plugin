package eu.tribusmc.tribuskitpvp.base.kits.abilities;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.messages.ActionBar;
import eu.tribusmc.tribuskitpvp.gui.GUIItem;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.awt.*;

public class GrapplingHook implements IAbility  {

    public GrapplingHook() {
        System.out.println("ny instance");
    }

    @Override
    public String getInternalName() {
        return "Grappling Hook";
    }

    @Override
    public ItemStack getHoldingItem() {
        return new GUIItem("hook", XMaterial.FISHING_ROD).setTitle("§a§lGrappling Hook").getOutcome();
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
    public void onPlayerDamage() {
    }

    @Override
    public Outcome onFishingRoodHook(PlayerFishEvent e) {

        if (e.getHook().getLocation().subtract(0, 1, 0).getBlock().getType().isSolid() || e.getHook().getLocation().getBlock().getType().isSolid()) {
            pullEntityToLocation(e.getPlayer(), e.getHook().getLocation());
            return Outcome.SUCCESS;
        }

        return Outcome.FAIL;

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
