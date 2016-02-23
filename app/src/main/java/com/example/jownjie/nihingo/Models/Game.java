package com.example.jownjie.nihingo.Models;

import android.widget.TextView;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Game.Timer;
import com.example.jownjie.nihingo.Models.Modes.AdvancedGame;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;
import com.example.jownjie.nihingo.Models.Modes.BeginnerGame;
import com.example.jownjie.nihingo.Models.Modes.ExpertGame;
import com.example.jownjie.nihingo.Models.TopPlayer;

import java.io.Serializable;

/**
 * Created by hebi5 on 12/2/2015.
 * edited by User on 12/8/2015 : reconstructed class.
 */
public class Game implements Serializable {
    private TopPlayer topPlayer;
    private Timer timer;
    private BaseGame beginnerGame;
    private BaseGame advancedGame;
    private BaseGame expertGame;

    public Game(Game game) {
        this.topPlayer = game.getTopPlayer();
        this.timer = game.getTimer();
        this.beginnerGame = game.getBeginnerGame();
        this.advancedGame = game.getAdvancedGame();
        this.expertGame = game.getExpertGame();
    }

    public Game(DatabaseController dc) {
        beginnerGame = new BeginnerGame(BaseGame.MODE_BEGINNER,dc);
        advancedGame = new AdvancedGame(BaseGame.MODE_ADVANCED,dc);
        expertGame = new ExpertGame(BaseGame.MODE_EXPERT,dc);
        topPlayer = new TopPlayer();
        timer = new Timer();
    }

    public void newT(TextView timer) {
        this.timer = new Timer(timer);
    }

    public TopPlayer getTopPlayer() {
        return topPlayer;
    }

    public Timer getTimer() {
        return timer;
    }

    public BaseGame getBeginnerGame() {
        return beginnerGame;
    }

    public void setBeginnerGameLevel(BaseGame beginnerGame) { this.beginnerGame.setCurrentLevel(beginnerGame.getCurrentLevel()); }

    public void setBeginnerGame(BaseGame beginnerGame) { this.beginnerGame = beginnerGame; }

    public BaseGame getAdvancedGame() {
        return advancedGame;
    }

    public void setAdvancedGameLevel(BaseGame advancedGame) { this.advancedGame.setCurrentLevel(advancedGame.getCurrentLevel()); }

    public void setAdvancedGame(BaseGame advancedGame) { this.advancedGame = advancedGame; }

    public BaseGame getExpertGame() {
        return expertGame;
    }

    public void setExpertGameLevel(BaseGame expertGame) { this.expertGame.setCurrentLevel(expertGame.getCurrentLevel()); }

    public void setExpertGame(BaseGame expertGame) { this.expertGame = expertGame; }
}
