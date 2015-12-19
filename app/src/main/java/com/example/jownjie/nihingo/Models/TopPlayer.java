package com.example.jownjie.nihingo.Models;

import java.io.Serializable;

/**
 * Created by User on 12/8/2015.
 */
public class TopPlayer implements Serializable {
    private int gamePoints;
    private String playerName;

    public TopPlayer() {
    }

    public TopPlayer(int gamePoints, String playerName) {
        this.gamePoints = gamePoints;
        this.playerName = playerName;
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
}
