package eu.tribusmc.tribuskitpvp.base;

import com.cryptomorin.xseries.messages.ActionBar;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CooldownParser {

    private final List<Player> cooldowns = new ArrayList<>();
    private Timer timer;
    private String tick, finish;


    public void parse(Player player, int cooldownTime, String internal,  RunnableCode runnableCode) {
        if (cooldowns.contains(player)) {
            return;
        }


        timer = new Timer(Timer.TimerType.REPEATABLE, cooldownTime);

        timer.execute(time -> {
            if (tick != null) ActionBar.sendActionBar(player, tick.replaceAll("%time%", time + "").replaceAll("%internal%", internal));
        }).whenFinished(time -> {
            cooldowns.remove(player);

            if (finish != null) ActionBar.sendActionBar(player, finish);

        }).run(true);


        runnableCode.run();
        cooldowns.add(player);
    }

    public CooldownParser setTickMessage(String tick) {
        this.tick = tick;
        return this;
    }

    public CooldownParser setFinishMessage(String finish) {
        this.finish = finish;
        return this;
    }

    public String getFinish() {
        return finish;
    }

    public String getTick() {
        return tick;
    }

    public void clear(Player player) {
        cooldowns.remove(player);
        timer.stop(true);

    }


    public interface RunnableCode {

        void run();

    }
}
