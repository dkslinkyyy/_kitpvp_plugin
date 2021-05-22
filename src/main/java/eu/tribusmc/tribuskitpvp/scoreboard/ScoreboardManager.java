package eu.tribusmc.tribuskitpvp.scoreboard;


import eu.tribusmc.tribuskitpvp.scoreboard.boards.LobbyScoreboard;

public class ScoreboardManager {


    private LobbyScoreboard lobbyScoreboard;


    public ScoreboardManager() {

        this.lobbyScoreboard = new LobbyScoreboard();
    }


    public LobbyScoreboard getLobbyScoreboard() {
        return lobbyScoreboard;
    }

}
