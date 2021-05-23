package eu.tribusmc.tribuskitpvp.base.ability.abilities;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.Core;
import eu.tribusmc.tribuskitpvp.base.ability.IAbility;
import eu.tribusmc.tribuskitpvp.gui.GUIItem;
import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SwitchPearl implements IAbility {

    private final ArrayList<Snowball> switchPearls = new ArrayList<>();

    @Override
    public String getInternalName() {
        return "Switch-Pearl";
    }

    @Override
    public ItemStack getHoldingItem() {
        return new GUIItem("switchpearl", XMaterial.SNOWBALL).glow().hideAttributes().setTitle("§b§lSwitch-Pearl").getOutcome();
    }

    @Override
    public boolean bypassCooldown() {
        return false;
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

        if (!(e.getDamager() instanceof Snowball)) return false;
        if (!(e.getEntity() instanceof Player)) return false;

        Snowball snowball = (Snowball) e.getDamager();

        if (!switchPearls.contains(snowball)) return false;

        e.setCancelled(true);

        Player damagedPlayer = (Player) e.getEntity();
        Player switchPearlThrower = (Player) snowball.getShooter();

        Location damagedPlayerLoc = damagedPlayer.getLocation();
        Location switchPearlThrowerLoc = switchPearlThrower.getLocation();

        damagedPlayer.teleport(switchPearlThrowerLoc);
        switchPearlThrower.teleport(damagedPlayerLoc);

        Particle.send(damagedPlayer, EnumParticle.SPELL_WITCH, new Float[]{0.5f, 0.5f, 0.5f}, 0.2f, 20, true);
        Particle.send(switchPearlThrower, EnumParticle.SPELL_WITCH, new Float[]{0.5f, 0.5f, 0.5f}, 0.2f, 20, true);

        Bukkit.getOnlinePlayers().forEach(oP -> {
            oP.playSound(damagedPlayerLoc, Sound.ENDERMAN_TELEPORT, 1.5f, 1);
        });

        switchPearls.remove(snowball);

        return false;

    }

    @Override
    public boolean onFishingRoodHook(PlayerFishEvent e) {
        return false;
    }

    @Override
    public boolean onPlaceBlock(BlockPlaceEvent e) {
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

        if (!(e.getEntity() instanceof Snowball)) return false;

        Snowball snowball = (Snowball) e.getEntity();
        Player shooter = (Player) e.getEntity().getShooter();

        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.i, () -> {
          shooter.getInventory().setItem(shooter.getInventory().getHeldItemSlot(), getHoldingItem());
        }, 1L);

        switchPearls.add(snowball);

        return true;

    }

    @Override
    public boolean onProjectileHit(ProjectileHitEvent e) {

        if(!(e.getEntity() instanceof Snowball)) return false;

        Snowball snowball = (Snowball) e.getEntity();

        if (switchPearls.contains(snowball)) switchPearls.remove(snowball);

        return false;

    }

    @Override
    public boolean onFlightToggle(PlayerToggleFlightEvent e) {
        return false;
    }

}

