package eu.tribusmc.tribuskitpvp.miscelleanous;

import com.cryptomorin.xseries.messages.ActionBar;
import eu.tribusmc.tribuskitpvp.miscelleanous.timer.Timer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CooldownParser {

    private final HashMap<Player, Timer> cooldowns;

    public CooldownParser() {
        cooldowns = new HashMap<>();
    }


    private Timer timer;
    private String tick, finish;


    public void parse(Player player, int cooldownTime, String internal, RunnableCode runnableCode) {

        if (!runnableCode.run()) return;

        if (cooldowns.containsKey(player)) {
            return;
        }

        Timer timer = new Timer(Timer.TimerType.REPEATABLE, cooldownTime, 20).execute(time -> {
            if (tick != null)
                ActionBar.sendActionBar(player, tick.replaceAll("%time%", time + "").replaceAll("%internal%", internal));
        }).whenFinished(time -> {
            cooldowns.remove(player);

            if (finish != null) ActionBar.sendActionBar(player, finish);

        }).run(true);

        cooldowns.put(player, timer);
    }

    public boolean isParsed(Player player) {
        return cooldowns.containsKey(player);
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

        if (cooldowns.get(player) == null) return;
        Timer t = cooldowns.get(player);
        System.out.println(t.getTime());
        t.stop(true);
        cooldowns.remove(player);
    }


    public interface RunnableCode {

        boolean run();

    }
}
