package eu.tribusmc.tribuskitpvp.base.effects;

import org.bukkit.entity.Player;

public class LightningDeathEffect extends DeathEffect {

    @Override
    public void execute(Player victim) {
        victim.getWorld().strikeLightningEffect(victim.getLocation());
        canExecute(true);

    }



}
