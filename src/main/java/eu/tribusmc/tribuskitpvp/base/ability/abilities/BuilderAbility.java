package eu.tribusmc.tribuskitpvp.base.ability.abilities;

import com.cryptomorin.xseries.XMaterial;
import eu.tribusmc.tribuskitpvp.base.ability.IAbility;
import eu.tribusmc.tribuskitpvp.gui.GUIItem;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.*;
import org.bukkit.block.Block;
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
import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;

import java.util.ArrayList;
import java.util.List;

public class BuilderAbility implements IAbility {

    @Override
    public String getInternalName() {
        return "builder-ability";
    }

    @Override
    public ItemStack getHoldingItem() {
        return new GUIItem("builder-ability", XMaterial.GLASS).setTitle("§d§lMagiskt Block").setAmount(10).getOutcome();
    }

    @Override
    public boolean bypassCooldown() {
        return true;
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
        return false;
    }


    @Override
    public boolean onPlaceBlock(BlockPlaceEvent e) {

        final Player p = e.getPlayer();
        final Block b = e.getBlock();

        ItemStack item = getHoldingItem();
        item.setAmount(1);

        if (e.getBlockPlaced().getType() != item.getType()) return false;

        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 4);
        Timer timer2 = new Timer(Timer.TimerType.DELAY, 9);

        timer.execute(time -> {

            b.setType(Material.STAINED_CLAY);
            b.setData(woolData[time-1].getData());

        }).whenFinished(time -> {

            Bukkit.getOnlinePlayers().forEach(o -> {
                o.playSound(b.getLocation(), Sound.DIG_STONE, 1.5f, 1);
            });

            Particle.send(p, b.getLocation().add(0.5, 0.5, 0.5), EnumParticle.CLOUD, new Float[]{0.3f, 0.3f, 0.3f}, 0f, 5, true);

            b.setType(Material.AIR);

        }).run(true);

        timer2.execute(time -> {
            p.getInventory().addItem(item);
        }).run(true);

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

    DyeColor[] woolData = {
            DyeColor.RED,
            DyeColor.ORANGE,
            DyeColor.YELLOW,
            DyeColor.LIME
    };

}
