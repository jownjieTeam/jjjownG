package com.example.jownjie.nihingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jownjie.nihingo.Models.Game;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DifficultySelectionActivity extends AppCompatActivity {

    private Game game;
    private BaseGame baseGame;
    private int gameMode;
    private int currentLevel;
    private int questionsSize;
    private int REQUEST_CODE = 0;
    private int RESULT_CODE = 1;

    @Bind(R.id.fiveLetters)
    TextView fiveLetters;

    @Bind(R.id.sevenLetters)
    TextView sevenLetters;

    @Bind(R.id.nineLetters)
    TextView nineLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection);
        ButterKnife.bind(this);

        game = (Game)getIntent().getExtras().getSerializable("GAME");
        gameMode = getIntent().getExtras().getInt("GAME_MODE");
        switch(gameMode){
            case 0:
                questionsSize = game.getBeginnerGame().getQuestionsSize();
                break;
            case 1:
                questionsSize = game.getAdvancedGame().getQuestionsSize();
                break;
            case 2:
                questionsSize = game.getExpertGame().getQuestionsSize();
                break;
        }
    }
}
