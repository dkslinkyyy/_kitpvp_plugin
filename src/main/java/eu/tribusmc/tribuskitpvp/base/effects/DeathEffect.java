package eu.tribusmc.tribuskitpvp.base.effects;

import eu.tribusmc.tribuskitpvp.base.BaseImpl;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.entity.Player;

public abstract class DeathEffect {


    private boolean canExecute = false;

    public abstract void execute(Player victim);

    public void invoke(BaseImpl base, Player victim, Player killer, boolean skipDeath) {

        victim.setHealth(20);


        Timer timer = new Timer(Timer.TimerType.REPEATABLE, 5);

        execute(victim);

        timer.execute(time -> {
            if (canExecute) {

                if(!skipDeath) {
                    base.kill(victim, killer);
                }else{
                    base.teleportToLobby(victim);

                }
                canExecute(false);

            }


        }).resetAtFinish(true).run(true);


    }

    protected void canExecute(boolean execute) {
        this.canExecute = execute;
    }

}
