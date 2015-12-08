package com.example.jownjie.nihingo.Models;

/**
 * Created by User on 12/2/2015.
 * edited by User on 12/8/2015 : removed other attributes except optionsPreference.
 */
public class BaseGame {
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
