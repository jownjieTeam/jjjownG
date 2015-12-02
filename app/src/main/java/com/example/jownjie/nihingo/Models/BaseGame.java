package com.example.jownjie.nihingo.Models;

/**
 * Created by Andrew Paul Mago on 12/2/2015.
 */
public class BaseGame {
    private int gamePoints;
    private int easyLevel;
    private int hardLevel;
    private String[] optionsPreference;

    public BaseGame() {
    }

    public BaseGame(int gamePoints, int easyLevel, int hardLevel, String[] optionsPreference) {
        this.gamePoints = gamePoints;
        this.easyLevel = easyLevel;
        this.hardLevel = hardLevel;
        this.optionsPreference = optionsPreference;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public void setGamePoints(int gamePoints) {
        this.gamePoints = gamePoints;
    }

    public int getEasyLevel() {
        return easyLevel;
    }

    public void setEasyLevel(int easyLevel) {
        this.easyLevel = easyLevel;
    }

    public int getHardLevel() {
        return hardLevel;
    }

    public void setHardLevel(int hardLevel) {
        this.hardLevel = hardLevel;
    }

    public String[] getOptionsPreference() {
        return optionsPreference;
    }

    public void setOptionsPreference(String[] optionsPreference) {
        this.optionsPreference = optionsPreference;
    }
}
