package com.example.jownjie.nihingo.Models;

/**
 * Created by User on 12/8/2015.
 */
public class TopPlayer {
    private int gamePoints;
    private String playerName;
    private int gameMode;

    public TopPlayer() {
    }

    public TopPlayer(int gamePoints, String playerName, int gameMode) {
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

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
}
