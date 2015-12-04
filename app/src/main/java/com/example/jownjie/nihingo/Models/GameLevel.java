package com.example.jownjie.nihingo.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by hebi5 on 12/2/2015.
 */
public class GameLevel {

    private Bitmap imageDr;
    private boolean answered;
    private int level;
    private String answer;

    public GameLevel(){}

    public GameLevel(Bitmap imageDr, boolean answered, int level, String answer) {
        this.imageDr = imageDr;
        this.answered = answered;
        this.level = level;
        this.answer = answer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public Bitmap getImageDr() {
        return imageDr;
    }

    public int getLevel() {
        return level;
    }

    public String getAnswer() {
        return answer;
    }

    public void setImageDr(byte[] imageDr) {
        Bitmap temp = BitmapFactory.decodeByteArray(imageDr,0,imageDr.length);
        this.imageDr = temp;
    }

    public void setAnswered(int answered) {
        this.answered = (( answered== 1 ) ? true : false);
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setLevel(int level) { this.level = level; }

}
