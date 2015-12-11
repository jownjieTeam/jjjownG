package com.example.jownjie.nihingo.Models.Modes;

import android.widget.TextView;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Game.Timer;
import com.example.jownjie.nihingo.Models.BaseGame;
import com.example.jownjie.nihingo.Models.GamePool;
import com.example.jownjie.nihingo.Models.TopPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hebi5 on 12/2/2015.
 * edited by User on 12/8/2015 : reconstructed class.
 */
public abstract class Game {
    private List<GamePool> questionsPool;
    private TopPlayer tp;
    private DatabaseController dc;
    private Timer t;

    public Game(DatabaseController dc) {
        this.dc = dc;
    }

    public List<GamePool> getQuestionsPool() {
        return questionsPool;
    }

    public void setQuestionsPool(List<GamePool> questionsPool) { this.questionsPool = questionsPool; }

    public void setQuestionsPool(int gameMode) {
        List<GamePool> gamePoolList = new ArrayList<GamePool>();
        gamePoolList = dc.getGamePool(gameMode);
        this.questionsPool = gamePoolList;
    }

    public TopPlayer getTp() {
        return tp;
    }

    public void setTp(TopPlayer tp) {
        this.tp = tp;
    }

    public Timer getT() {
        return t;
    }

    public void setT(Timer t) {
        this.t = t;
    }

    public void setT(TextView timer) {
        this.t = new Timer(timer);
    }

    public List<String> randomize(String answer) {return null;}

    public DatabaseController getDc() {
        return dc;
    }

    public void setDc(DatabaseController dc) {
        this.dc = dc;
    }


    public abstract void play();
}
