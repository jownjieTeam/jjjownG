package com.example.jownjie.nihingo.Models;

/**
 * Created by User on 12/2/2015.
 * edited by User on 12/8/2015 : removed other attributes except optionsPreference.
 */
public class BaseGame {

    public static final int MODE_BEGINNER = 0;
    public static final int MODE_ADVANCED = 1;
    public static final int MODE_EXPERT = 2;
    public static final int MODE_NULL = 3;

    private String[] optionsPreference;

    public BaseGame() {
    }

    public BaseGame( String[] optionsPreference) {
        this.optionsPreference = optionsPreference;
    }

    public String[] getOptionsPreference() {
        return optionsPreference;
    }

    public void setOptionsPreference(String[] optionsPreference) {
        this.optionsPreference = optionsPreference;
    }
}
