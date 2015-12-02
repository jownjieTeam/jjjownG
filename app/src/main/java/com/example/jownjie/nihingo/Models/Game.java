package com.example.jownjie.nihingo.Models;

/**
 * Created by hebi5 on 12/2/2015.
 */
abstract class Game extends BaseGame {
    private int currentLevel;

    public Game(){}

    public Game(int currentLevel, int gamePoints, int easyLevel, int hardLevel, String[] optionsPreference){
        super(gamePoints, easyLevel, hardLevel, optionsPreference);

        this.currentLevel = currentLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public abstract void play();
    public abstract void buyClue(int gamePoints);
    public abstract String generateRandomLetters();
}
