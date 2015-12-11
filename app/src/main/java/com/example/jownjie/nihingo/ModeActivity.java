package com.example.jownjie.nihingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.jownjie.nihingo.Models.BaseGame;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModeActivity extends AppCompatActivity {

    private int gameMode;

    @Bind(R.id.button_beginner)
    Button beginner;

    @Bind(R.id.button_advanced)
    Button advanced;

    @Bind(R.id.button_expert)
    Button expert;

    @OnClick(R.id.button_beginner)
    public void setBeginnerMode() {
        this.gameMode = BaseGame.MODE_BEGINNER;
        startActivity(new Intent(this,GameActivity.class).putExtra("GAME_MODE",gameMode));
    }

    @OnClick(R.id.button_advanced)
    public void setAdvancedMode() {
        this.gameMode = BaseGame.MODE_ADVANCED;
        startActivity(new Intent(this,GameActivity.class).putExtra("GAME_MODE",gameMode));
    }

    @OnClick(R.id.button_expert)
    public void setExpertMode() {
        this.gameMode = BaseGame.MODE_EXPERT;
        startActivity(new Intent(this,GameActivity.class).putExtra("GAME_MODE",gameMode));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        ButterKnife.bind(this);
    }
}
