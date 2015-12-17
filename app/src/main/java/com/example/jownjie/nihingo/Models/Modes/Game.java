package com.example.jownjie.nihingo.Models.Modes;

import android.widget.TextView;

import com.example.jownjie.nihingo.Database.DatabaseConnector;
import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Game.Random;
import com.example.jownjie.nihingo.Game.Timer;
import com.example.jownjie.nihingo.HomeScreen;
import com.example.jownjie.nihingo.Models.BaseGame;
import com.example.jownjie.nihingo.Models.GamePool;
import com.example.jownjie.nihingo.Models.TopPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hebi5 on 12/2/2015.
 * edited by User on 12/8/2015 : reconstructed class.
 */
public class Game implements Serializable {
    private TopPlayer topPlayer;
    private Timer timer;
    private BeginnerGame beginnerGame;
    private AdvancedGame advancedGame;
    private ExpertGame expertGame;

    public Game() {
        beginnerGame = new BeginnerGame();
        advancedGame = new AdvancedGame();
        expertGame = new ExpertGame();
        topPlayer = new TopPlayer();
    }

    public void newT(TextView timer) {
        this.timer = new Timer(timer);
    }

    public TopPlayer getTopPlayer() {
        return topPlayer;
    }

    public void setTopPlayer(TopPlayer topPlayer) {
        this.topPlayer = topPlayer;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public BeginnerGame getBeginnerGame() {
        return beginnerGame;
    }

    public void setBeginnerGame(BeginnerGame beginnerGame) {
        this.beginnerGame = beginnerGame;
    }

    public AdvancedGame getAdvancedGame() {
        return advancedGame;
    }

    public void setAdvancedGame(AdvancedGame advancedGame) {
        this.advancedGame = advancedGame;
    }

    public ExpertGame getExpertGame() {
        return expertGame;
    }

    public void setExpertGame(ExpertGame expertGame) {
        this.expertGame = expertGame;
    }
}
