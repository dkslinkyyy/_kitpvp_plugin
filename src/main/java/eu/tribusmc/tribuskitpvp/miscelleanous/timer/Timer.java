package eu.tribusmc.tribuskitpvp.miscelleanous.timer;

import eu.tribusmc.tribuskitpvp.Core;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {


    private TimerType timerType;
    /**
     * Timer class for better Runnable implementation.
     *
     * @Author Slinkyyy
     */


    private final int tmpTime;
    private int time;

    private boolean reset, run;

    private RunnableCode whenFinished, executable;


    public Timer(TimerType timerType, int time) {
        this.timerType = timerType;
        this.time = time == 0 ? time = 10 : time;
        this.tmpTime = time;

        run();
    }

    public Timer resetAtFinish(boolean reset) {
        this.reset = reset;

        return this;
    }

    public int getTime() {
        return time;
    }

    public Timer execute(RunnableCode runnableCode) {
        this.executable = runnableCode;
        return this;
    }

    public Timer whenFinished(RunnableCode whenFinished) {
        this.whenFinished = whenFinished;
        return this;
    }

    public Timer run(boolean run) {
        this.run = run;
        return this;
    }


    private void run() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if(!run) return;
                switch (timerType) {
                    case DELAY:
                        if (time == 0) {
                            executable.run(time);
                            if (reset) {
                                resetTime();
                            } else {
                                this.cancel();

                            }
                        }
                        time--;
                        break;
                    case REPEATABLE:
                        if (time == 0) {
                            if (whenFinished != null) whenFinished.run(time);
                            if (reset) {
                                resetTime();
                            } else {
                                this.cancel();

                            }
                        }

                        if(executable !=null) executable.run(time);
                        time--;
                        break;
                }
            }
        }.runTaskTimer(Core.i, 0, 20L);
    }

    private void resetTime() {
        this.time = tmpTime;
    }

    public interface RunnableCode {

        void run(int time);

    }

    public enum TimerType {
        REPEATABLE, DELAY;


    }


}
