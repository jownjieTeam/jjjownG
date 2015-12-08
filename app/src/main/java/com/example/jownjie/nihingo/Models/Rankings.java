package com.example.jownjie.nihingo.Models;

/**
 * Created by User on 12/8/2015.
 */
public class Rankings {
    private int gamePoints;
    private String playerName;
    private String gameMode;

    public Rankings() {
    }

    public Rankings(int gamePoints, String playerName, String gameMode) {
        this.gamePoints = gamePoints;
        this.playerName = playerName;
        this.gameMode = gameMode;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public void setGamePoints(int gamePoints) {
        this.gamePoints = gamePoints;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}
