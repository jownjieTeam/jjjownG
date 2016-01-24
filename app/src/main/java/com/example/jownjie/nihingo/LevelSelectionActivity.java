package com.example.jownjie.nihingo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.jownjie.nihingo.Models.Game;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LevelSelectionActivity extends AppCompatActivity implements View.OnClickListener{
    private Game game;
    private BaseGame baseGame;
    private int gameMode;
    private int currentLevel;
    private int questionsSize;
    private int REQUEST_CODE = 0;
    private int RESULT_CODE = 1;

    @Bind(R.id.table_layout)
    TableLayout tableLayout;

    Button[] levelBtnList;
    TableRow[] tbls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

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

        inflateLevels();
    }

    public void inflateLevels(){
        int ctr = 1;
        tbls = new TableRow[(questionsSize%2==0)?questionsSize/3:(questionsSize/3)+1];
        levelBtnList = new Button[questionsSize];


        for(int i = 0; i < tbls.length; i++) {
            tbls[i] = new TableRow(this);
            tableLayout.addView(tbls[i]);
            for(int j = 0; j < 3 && ctr <= questionsSize; j++) {
                levelBtnList[i] = new Button(this, null, android.R.attr.buttonStyleSmall);
                levelBtnList[i].setText(ctr + "");
                levelBtnList[i].setOnClickListener(this);
                tbls[i].addView(levelBtnList[i]);
                ctr++;
            }
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("GAME_MODE",gameMode);
        intent.putExtra("TOP_PLAYER", game.getTopPlayer().getGamePoints());
        intent.putExtra("CURRENT_LEVEL", currentLevel);
        intent.putExtra("TIMER", game.getTimer().getTotalTime());
        this.setResult(RESULT_CODE,intent);
        super.finish();
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
                int newLevel = data.getExtras().getInt("CURRENT_LEVEL");
                if(currentLevel < newLevel)
                    currentLevel = data.getExtras().getInt("CURRENT_LEVEL");
                switch(gameMode) {
                    case BaseGame.MODE_BEGINNER : game.getBeginnerGame().setCurrentLevel(currentLevel);
                        if(currentLevel==newLevel) {
                        }
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
    public void onClick(View v) {
        Button button = (Button)v;
        int level = Integer.parseInt(button.getText().toString())-1;

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("GAME_MODE", gameMode);
        intent.putExtra("GAME", game);
        intent.putExtra("CURRENT_LEVEL",level);
        startActivityForResult(intent,REQUEST_CODE);
    }
}
