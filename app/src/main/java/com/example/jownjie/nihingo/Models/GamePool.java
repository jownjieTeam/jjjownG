package com.example.jownjie.nihingo.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by hebi5 on 12/2/2015.
 * edited by User on 12/5/2015 : added hint, renamed from GameLevel->GamePool.
 */
public class GamePool {

    private Bitmap imageDr;
    private String answer;
    private String hint;

    public GamePool(){}

    public GamePool(Bitmap imageDr, String answer, String hint) {
        this.imageDr = imageDr;
        this.answer = answer;
        this.hint = hint;
    }

    public Bitmap getImageDr() {
        return imageDr;
    }

    public String getAnswer() {
        return answer;
    }

    public String getHint() {
        return hint;
    }

    public void setImageDr(byte[] imageDr) {
        Bitmap temp = BitmapFactory.decodeByteArray(imageDr, 0, imageDr.length);
        this.imageDr = temp;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
