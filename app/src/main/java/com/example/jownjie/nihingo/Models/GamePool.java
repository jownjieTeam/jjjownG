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
    private int soundRes;
    private String answer;
    private String hint;
    private int gameMode;
    private int level;

    public GamePool(){}

    public GamePool(int imageRes, int soundRes, String imageDr, String hint) {
        String[] splitString = imageDr.split("_");
        this.imageRes = imageRes;
        this.soundRes = soundRes;
        this.answer = splitString[1];
        this.hint = hint;
        this.gameMode = getValue(splitString[0]);
        this.level = Integer.valueOf(splitString[2]);
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getSoundRes() {
        return soundRes;
    }

    public void setSoundRes(int soundRes) {
        this.soundRes = soundRes;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAnswerResource(String answer) {
        this.answer = answer.split("_")[1];
    }

    public void setGameMode(String gameMode) {
        this.gameMode = getValue(gameMode.split("_")[0]);
    }

    public void setLevelResource(String level) {
        this.level = Integer.valueOf(answer.split("_")[2]);
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

    @Override
    public String toString() {
        return "ImageRes: " + this.imageRes + "\nAudioRes: " + this.soundRes + "\nHint: " + this.hint + "\nAnswer: " + this.answer + "\nGameMode: " + this.gameMode + "\nLevel :" + this.level;
    }
}
