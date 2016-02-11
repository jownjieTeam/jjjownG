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
 * edited by User on 1/22/2015 : other changes not recorded.
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
    private List<GamePool> gameQuestions_SHORT;
    private List<GamePool> gameQuestions_MEDIUM;
    private List<GamePool> gameQuestions_LONG;


    public abstract int getPoints(int seconds, int totalTime);

    public BaseGame() {
        questionsPool = new ArrayList<>();
        gameQuestions_SHORT = new ArrayList<>();
        gameQuestions_MEDIUM = new ArrayList<>();
        gameQuestions_LONG = new ArrayList<>();
    }

    public BaseGame(int gameMode,DatabaseController dc) {
        questionsPool = new ArrayList<>();
        gameQuestions_SHORT = new ArrayList<>();
        gameQuestions_MEDIUM = new ArrayList<>();
        gameQuestions_LONG = new ArrayList<>();
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

    public List<GamePool> getGameQuestions_SHORT() {
        return gameQuestions_SHORT;
    }

    public List<GamePool> getGameQuestions_MEDIUM() {
        return gameQuestions_MEDIUM;
    }

    public List<GamePool> getGameQuestions_LONG() {
        return gameQuestions_LONG;
    }

    public void setGameQuestions_SHORT(List<GamePool> gameQuestions_SHORT) {
        this.gameQuestions_SHORT = gameQuestions_SHORT;
    }

    public void setGameQuestions_MEDIUM(List<GamePool> gameQuestions_MEDIUM) {
        this.gameQuestions_MEDIUM = gameQuestions_MEDIUM;
    }

    public void setGameQuestions_LONG(List<GamePool> gameQuestions_LONG) {
        this.gameQuestions_LONG = gameQuestions_LONG;
    }

    public int getAccomplished(int gameQuestions_LENGTH) {
        int counter = 0;
        switch(gameQuestions_LENGTH) {
            case POOL_SHORT: for(GamePool gp : gameQuestions_SHORT) {
                                if(gp.isAnswered())
                                    counter++;
                            }
                break;
            case POOL_MEDIUM:  for(GamePool gp : gameQuestions_MEDIUM) {
                                if(gp.isAnswered())
                                    counter++;
                            }
                break;
            case POOL_LONG:     for(GamePool gp : gameQuestions_LONG) {
                                if(gp.isAnswered())
                                    counter++;
                            }
                break;
            default:    Log.e("GAME ERROR", "LENGTH IS INVALID");
                break;
        }

        return counter;
    }

    public List<Integer> getLevelsAccomplished(int gameQuestions_LENGTH) {
        List<Integer> accomplished = new ArrayList<>();
        switch(gameQuestions_LENGTH) {
            case POOL_SHORT: for(GamePool gp : gameQuestions_SHORT) {
                if(gp.isAnswered())
                    accomplished.add(gp.getLevel());
            }
                break;
            case POOL_MEDIUM:  for(GamePool gp : gameQuestions_MEDIUM) {
                if(gp.isAnswered())
                    accomplished.add(gp.getLevel());
            }
                break;
            case POOL_LONG:     for(GamePool gp : gameQuestions_LONG) {
                if(gp.isAnswered())
                    accomplished.add(gp.getLevel());
            }
                break;
            default:    Log.e("GAME ERROR", "LENGTH IS INVALID");
                break;
        }

        return accomplished;
    }

    public boolean isAccomplished() {
        if(currentLevel==questionsSize)
            return true;
        return false;
    }

    public GamePool getCurrentQuestion() {
        return questionsPool.get(currentLevel-1);
    }

    public GamePool getNextLevel() {
        currentLevel++;
        if(currentLevel<=questionsSize) {
            for(GamePool gp : questionsPool) {
                if(gp.getLevel()==currentLevel) {
                    return gp;
                }
            }
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
        List<GamePool> gamePoolList = dc.getGamePool(gameMode);
        int SHORTCOUNTER = 1, MEDIUMCOUNTER = 1, LONGCOUNTER = 1, ERRORCOUNTER = 1;
        while(!gamePoolList.isEmpty()) {
            final int randomIndex = Random.getRandomNumber(gamePoolList.size());
            try {
                GamePool gp = gamePoolList.get(randomIndex);
                switch(gp.getClassification()) {
                    case POOL_SHORT : gp.setLevel(SHORTCOUNTER);
                                        SHORTCOUNTER++;
                                        gameQuestions_SHORT.add(gp);
                                        break;
                    case POOL_MEDIUM: gp.setLevel(MEDIUMCOUNTER);
                                        MEDIUMCOUNTER++;
                                        gameQuestions_MEDIUM.add(gp);
                                        break;
                    case POOL_LONG: gp.setLevel(LONGCOUNTER);
                                        LONGCOUNTER++;
                                        gameQuestions_LONG.add(gp);
                                        break;
                    default: ERRORCOUNTER++;
                            break;
                }
                this.questionsPool.add(gp);
                Log.e("GAME POOL", gp.toString());
                gamePoolList.remove(randomIndex);
            } catch(NullPointerException npe) {
                npe.printStackTrace();
            }
        }
        Log.e("ERROR ", ERRORCOUNTER+"");
    }
}