package com.example.jownjie.nihingo.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by hebi5 on 12/2/2015.
 */
public class GameLevel {
    private long id;
    private Drawable imageDr;
    private boolean answered;
    private final int level = 0;
    private String answer;

    public GameLevel(){

    }

    public long getId() {
        return id;
    }

    public boolean isAnswered() {
        return answered;
    }

    public Drawable getImageDr() {
        return imageDr;
    }

    public int getLevel() {
        return level;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImageDr(Drawable imageDr) {
        this.imageDr = imageDr;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
