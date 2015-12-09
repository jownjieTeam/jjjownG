package com.example.jownjie.nihingo.Models.Modes;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Models.GamePool;

import java.util.List;

/**
 * Created by User on 12/10/2015.
 */
public class AdvancedGame extends Game {

    DatabaseController dc;

    public AdvancedGame(DatabaseController dc) {
        this.dc = dc;
    }

    @Override
    public void setQuestionsPool(List<GamePool> questionsPool) {

    }

    @Override
    public void play() {

    }
}
