package eu.tribusmc.tribuskitpvp.base.effects;

import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class FireworkDeathEffect extends DeathEffect {



    @Override
    public void execute(Player victim) {
        victim.setHealth(20.0);

        victim.setVelocity(new Vector(0, 1, 0));

        Bukkit.getOnlinePlayers().forEach(online -> {
            online.playSound(victim.getLocation(), Sound.FIREWORK_LAUNCH, 1, 1);
        });

        Timer timer = new Timer(Timer.TimerType.DELAY, 1);


        timer.execute(time -> {
            Bukkit.getOnlinePlayers().forEach(online -> {
                online.playSound(victim.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
                spawnFirework(victim.getLocation(), 2);
                canExecute(true);
            });
        }).run(true);
    }


    public void spawnFirework(Location loc, int power) {

        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        FireworkEffect.Builder builder = FireworkEffect.builder();

        builder.withTrail().withFlicker().withColor(Color.GREEN).with(FireworkEffect.Type.BURST);

        fwm.addEffect(builder.build());
        fwm.setPower(power);

        fw.setFireworkMeta(fwm);

        fw.detonate();
    }


}
