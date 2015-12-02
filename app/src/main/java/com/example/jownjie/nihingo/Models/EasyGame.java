package com.example.jownjie.nihingo.Models;

import java.util.List;

/**
 * Created by hebi5 on 12/2/2015.
 */
public class EasyGame extends Game {
    private List<GameLevel> questions;

    public EasyGame(int currentLevel, int gamePoints, int easyLevel, int hardLevel, String[] optionsPreference){
        super(currentLevel, gamePoints, easyLevel, hardLevel, optionsPreference);
        currentLevel = easyLevel;
    }

    @Override
    public void play() {

    }

    @Override
    public void buyClue(int gamePoints) {

    }

    @Override
    public String generateRandomLetters() {
        return null;
    }
}
