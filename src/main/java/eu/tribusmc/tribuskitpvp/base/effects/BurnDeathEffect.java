package eu.tribusmc.tribuskitpvp.base.effects;

import eu.tribusmc.tribuskitpvp.miscelleanous.Particle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class BurnDeathEffect extends DeathEffect {

    @Override
    public void execute(Player victim) {
        Particle.send(victim, EnumParticle.FLAME, new Float[]{0.2f, 0.2f, 0.2f}, 0.1f, 25, true);
        Particle.send(victim, EnumParticle.LAVA, new Float[]{0.5f, 0.5f, 0.5f}, 0.1f, 25, true);
        Bukkit.getOnlinePlayers().forEach(online -> {
            online.playSound(victim.getLocation(), Sound.FIZZ, 2, 1);
            online.playSound(victim.getLocation(), Sound.LAVA_POP, 2, 1);
        });
        canExecute(true);
    }



}
