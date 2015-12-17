package com.example.jownjie.nihingo.Models.Modes;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Game.Random;
import com.example.jownjie.nihingo.HomeScreen;
import com.example.jownjie.nihingo.Models.BaseGame;
import com.example.jownjie.nihingo.Models.GamePool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/10/2015.
 */
public class AdvancedGame extends BaseGame implements Serializable {

    private final int SCORE_PERFECT = 15;
    private final int SCORE_PARTIAL = 20;
    private final int ALLOTED_TIME  = 180;

    private int questionsSize;
    private int currentLevel;
    private List<GamePool> questionsPool;

    public AdvancedGame() {
        retrieveQuestionsPool();
        currentLevel = 0;
        questionsSize = questionsPool.size();
    }

    public int getQuestionsSize() {
        return questionsSize;
    }

    public void setQuestionsSize(int questionsSize) {
        this.questionsSize = questionsSize;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setQuestionsPool(List<GamePool> questionsPool) { this.questionsPool = questionsPool; }

    @Override
    public void retrieveQuestionsPool() {
        List<GamePool> gamePoolList = new ArrayList<GamePool>();
        gamePoolList = HomeScreen.dc.getGamePool(BaseGame.MODE_BEGINNER);
        int i = 1;
        while(!gamePoolList.isEmpty()) {
            final int randomIndex = Random.getRandomNumber(gamePoolList.size());
            try {
                gamePoolList.get(randomIndex).setLevel(i);
                this.questionsPool.add(gamePoolList.get(randomIndex));
                gamePoolList.remove(randomIndex);
                i++;
            } catch (NullPointerException npe) {
                continue;
            }
        }
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
