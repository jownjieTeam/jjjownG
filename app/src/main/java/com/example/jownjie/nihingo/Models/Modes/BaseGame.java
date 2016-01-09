package com.example.jownjie.nihingo.Models.Modes;

import android.util.Log;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Game.Random;
import com.example.jownjie.nihingo.Models.GamePool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/2/2015.
 * edited by User on 12/8/2015 : removed other attributes except optionsPreference.
 */
public abstract class BaseGame implements Serializable{

    public static final int MODE_BEGINNER = 0;
    public static final int MODE_ADVANCED = 1;
    public static final int MODE_EXPERT = 2;
    public static final int MODE_NULL = 3;

    public static final int POOL_SHORT = 0;
    public static final int POOL_MEDIUM = 1;
    public static final int POOL_LONG = 2;
    public static final int POOL_NULL = 3;

    private int questionsSize;
    private int currentLevel;
    private List<GamePool> questionsPool;


    public abstract int getPoints(int seconds, int totalTime);

    public BaseGame() {
        questionsPool = new ArrayList<>();
    }

    public BaseGame(int gameMode,DatabaseController dc) {
        questionsPool = new ArrayList<>();
        retrieveQuestionsPool(gameMode,dc);
        currentLevel = 0;
        questionsSize = questionsPool.size();
    }

    public int getQuestionsSize() {
        return questionsSize;
    }

    public List<GamePool> getGamePoolList() { return questionsPool; }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setQuestionsSize(int questionsSize) {
        this.questionsSize = questionsSize;
    }

    public List<GamePool> setGamePoolList(List<GamePool> gpl) {
        return questionsPool = gpl;
    }

    public boolean isAccomplished() {
        if((currentLevel+1)==questionsSize)
            return true;
        return false;
    }

    public GamePool getNextLevel() {
        if(currentLevel<questionsSize) {
            currentLevel++;
            return questionsPool.get(currentLevel - 1);
        }
        return null;
    }

    public List<GamePool> retrieveClass(int classification) {
        if(questionsPool.isEmpty())
            return null;

        List<GamePool> gamePoolList = new ArrayList<>();

        for(GamePool gp : questionsPool) {
            if(gp.getClassification()==classification) {
                gamePoolList.add(gp);
            }
        }

        return gamePoolList;
    }

    private void retrieveQuestionsPool(int gameMode,DatabaseController dc) {
        List<GamePool> gamePoolList = new ArrayList<GamePool>();
        gamePoolList = dc.getGamePool(gameMode);
        int i = 1;
        while(!gamePoolList.isEmpty()) {
            final int randomIndex = Random.getRandomNumber(gamePoolList.size());
            try {
                gamePoolList.get(randomIndex).setLevel(i);
                this.questionsPool.add(gamePoolList.get(randomIndex));
                Log.e("GAME POOL", gamePoolList.get(randomIndex).toString());
                gamePoolList.remove(randomIndex);
                i++;
            } catch(NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }
}
