package com.example.jownjie.nihingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jownjie.nihingo.Game.Timer;
import com.example.jownjie.nihingo.Models.Modes.AdvancedGame;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;
import com.example.jownjie.nihingo.Models.Game;
import com.example.jownjie.nihingo.Models.Modes.BeginnerGame;
import com.example.jownjie.nihingo.Models.Modes.ExpertGame;
import com.example.jownjie.nihingo.Models.TopPlayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModeActivity extends AppCompatActivity {

    private int gameMode;
    private Game game = null;
    private int currentLevel;
    private int REQUEST_CODE = 0;
    private int RESULT_CODE = 1;

    @Bind(R.id.playerName)
    EditText playerName;

    @Bind(R.id.beginnerResult)
    TextView beginner;

    @Bind(R.id.advancedResult)
    TextView advanced;

    @Bind(R.id.expertResult)
    TextView expert;

    @OnClick(R.id.level_beginner)
    public void setBeginnerMode() {
        this.gameMode = BaseGame.MODE_BEGINNER;
        currentLevel = game.getBeginnerGame().getCurrentLevel();
        startNewActivity();
    }

    @OnClick(R.id.level_advanced)
    public void setAdvancedMode() {
        this.gameMode = BaseGame.MODE_ADVANCED;
        currentLevel = game.getAdvancedGame().getCurrentLevel();
        startNewActivity();
    }

    @OnClick(R.id.level_expert)
    public void setExpertMode() {
        this.gameMode = BaseGame.MODE_EXPERT;
        currentLevel = game.getExpertGame().getCurrentLevel();
        startNewActivity();
    }

    private void startNewActivity() {
        if(playerName.getText().toString().length()!=0) {
            game.getTopPlayer().setPlayerName(playerName.getText().toString());
        }
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("GAME_MODE", gameMode);
        intent.putExtra("GAME", game);
        intent.putExtra("CURRENT_LEVEL",currentLevel);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        ButterKnife.bind(this);
        game = new Game(HomeScreen.dc);
        initProgress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_CODE == requestCode) {
            if(RESULT_CODE == resultCode) {
                int gamePoints = data.getExtras().getInt("TOP_PLAYER");
                    game.getTopPlayer().setGamePoints(gamePoints);
                int totalTime = data.getExtras().getInt("TIMER");
                    game.getTimer().setTotalTime(totalTime);
                gameMode = data.getExtras().getInt("GAME_MODE");
                currentLevel = data.getExtras().getInt("CURRENT_LEVEL");
                switch(gameMode) {
                    case BaseGame.MODE_BEGINNER : game.getBeginnerGame().setCurrentLevel(currentLevel);
                        break;
                    case BaseGame.MODE_ADVANCED : game.getAdvancedGame().setCurrentLevel(currentLevel);
                        break;
                    case BaseGame.MODE_EXPERT : game.getExpertGame().setCurrentLevel(currentLevel);
                        break;
                    case BaseGame.MODE_NULL : Log.e("ERROR", "UNIDENTIFIED GAME MODE!");
                        break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        initProgress();
        super.onResume();
    }

    private void initProgress() {
        beginner.setText((game.getBeginnerGame().getCurrentLevel()+1)+"/"+game.getBeginnerGame().getQuestionsSize());
        advanced.setText((game.getAdvancedGame().getCurrentLevel()+1)+"/"+game.getAdvancedGame().getQuestionsSize());
        expert.setText((game.getExpertGame().getCurrentLevel()+1)+"/"+game.getExpertGame().getQuestionsSize());
    }
}
