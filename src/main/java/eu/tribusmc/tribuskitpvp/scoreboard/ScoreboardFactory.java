package eu.tribusmc.tribuskitpvp.scoreboard;

import eu.tribusmc.tribuskitpvp.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreboardFactory {

    private ScoreboardManager scoreboardManager;
    private Scoreboard scoreboard;
    private Objective objective;
    private Score score;

    private HashMap<String, String> placeholders;
    private List<String> lines;
    private String title, footer;
    private int lastPos = 1;

    public ScoreboardFactory(String title, String footer) {
        this.title = title;
        this.footer = footer;
        this.placeholders = new HashMap<>();
        this.lines = new ArrayList<>();
        scoreboardManager = Bukkit.getScoreboardManager();
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void send(Player player) {
        player.sendMessage(scoreboard.toString());
        player.setScoreboard(scoreboard);
    }

    public void setTitle(String title) {
        this.title = title;
        this.objective.setDisplayName(title);
    }

    public String getTitle() {
        return title;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        score = objective.getScore(footer);
        score.setScore(0);

    }


    public ScoreboardFactory addLine(String line) {
        System.out.println(line);
        if(line.isEmpty()) {
            line = reset();
        }
        lines.add(line);
        score = objective.getScore(line);
        score.setScore(lastPos);
        lastPos++;
        return this;
    }

    private String previous = "";
    private String reset() {
        previous += ChatColor.RESET.toString();
        return previous += ChatColor.RESET.toString();
    }

    public ScoreboardFactory addPlaceholder(String placeholder, String text) {
        lastPos = 1;


       for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }


        for(int i = 0; i < lines.size(); i++){
            lines.set(i, lines.get(i).replaceAll(placeholder, text));
        }

        lines.forEach(line -> {
            score = objective.getScore(line);
            score.setScore(lastPos);
            lastPos++;

        });
        return this;
    }
    private void update() {




    }
}
