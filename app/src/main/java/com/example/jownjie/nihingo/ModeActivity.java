package com.example.jownjie.nihingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Models.BaseGame;
import com.example.jownjie.nihingo.Models.Modes.Game;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModeActivity extends AppCompatActivity {

    private int gameMode;
    private Game game;

    @Bind(R.id.beginnerResult)
    TextView beginner;

    @Bind(R.id.advancedResult)
    TextView advanced;

    @Bind(R.id.expertResult)
    TextView expert;

    @OnClick(R.id.level_beginner)
    public void setBeginnerMode() {
        this.gameMode = BaseGame.MODE_BEGINNER;
        startNewActivity();
    }

    @OnClick(R.id.level_advanced)
    public void setAdvancedMode() {
        this.gameMode = BaseGame.MODE_ADVANCED;
        startNewActivity();
    }

    @OnClick(R.id.level_expert)
    public void setExpertMode() {
        this.gameMode = BaseGame.MODE_EXPERT;
        startNewActivity();
    }

    private void startNewActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("GAME_MODE",gameMode);
        intent.putExtra("GAME",game);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        ButterKnife.bind(this);
        game = new Game();
        initProgress();
    }

    private void initProgress() {
        beginner.setText(game.getBeginnerGame().getCurrentLevel()+"/"+game.getBeginnerGame().getQuestionsSize());
        advanced.setText(game.getAdvancedGame().getCurrentLevel()+"/"+game.getAdvancedGame().getQuestionsSize());
        expert.setText(game.getExpertGame().getCurrentLevel()+"/"+game.getExpertGame().getQuestionsSize());
    }
}
