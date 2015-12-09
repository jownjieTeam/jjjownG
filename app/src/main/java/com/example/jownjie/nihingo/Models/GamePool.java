package com.example.jownjie.nihingo.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by hebi5 on 12/2/2015.
 * edited by User on 12/5/2015 : added hint, renamed from GameLevel->GamePool.
 */
public class GamePool {

    private int imageRes;
    private String answer;
    private String hint;
    private int gameMode;

    public GamePool(){}

    public GamePool(int imageRes, String imageDr, String hint) {
        this.imageRes = imageRes;
        this.answer = getAnswerValue(imageDr.split("_")[1]);
        this.hint = hint;
        this.gameMode = getValue(imageDr.split("_")[0]);
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswerResource(String answer) {
        this.answer = getAnswerValue(answer.split("_")[1]);
    }

    public void setAnswer(String answer) { this.answer = answer; }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = getValue(gameMode.split("_")[0]);
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    //helper methods
    private int getValue(String gameMode) {
        if(gameMode.contentEquals("beginner"))
            return BaseGame.MODE_BEGINNER;
        else if(gameMode.contentEquals("advanced"))
            return BaseGame.MODE_ADVANCED;
        else if(gameMode.contentEquals("expert"))
            return BaseGame.MODE_EXPERT;
        else
            return BaseGame.MODE_NULL;
    }

    private String getAnswerValue(String answer) {
        int index;
        String newAnswer;
        index = answer.lastIndexOf(".png");
        if(index > 0)
            return newAnswer = answer.substring(0, index);
        index = answer.lastIndexOf(".jpg");
        if(index > 0)
            return newAnswer = answer.substring(0, index);
        return null;
    }
}
