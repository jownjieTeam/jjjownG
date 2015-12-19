package com.example.jownjie.nihingo.Models.Modes;

import com.example.jownjie.nihingo.Database.DatabaseController;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 12/10/2015.
 */
public class AdvancedGame extends BaseGame implements Serializable {

    private final int SCORE_PERFECT = 10;
    private final int SCORE_PARTIAL = 15;
    private final int ALLOTED_TIME = 300;

    public AdvancedGame() {
        super();
    }

    public AdvancedGame(int gameMode,DatabaseController dc) {
        super(gameMode,dc);
    }

    @Override
    public int getPoints(int seconds, int totalTime) {
        if(totalTime <= ALLOTED_TIME) {
            if (seconds <= SCORE_PERFECT)
                return 3;
            else if (seconds <= SCORE_PARTIAL)
                return 2;
            else
                return 1;
        }
        return 0;
    }
}
