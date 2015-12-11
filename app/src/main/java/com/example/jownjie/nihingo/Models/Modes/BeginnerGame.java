package com.example.jownjie.nihingo.Models.Modes;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Models.BaseGame;
import com.example.jownjie.nihingo.Models.GamePool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/10/2015.
 */
public class BeginnerGame extends Game {

    public BeginnerGame(DatabaseController dc) {
        super(dc);
    }

    @Override
    public void play() {

    }
}
