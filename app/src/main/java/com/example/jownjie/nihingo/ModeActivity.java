package com.example.jownjie.nihingo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jownjie.nihingo.Models.Game;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModeActivity extends Activity {

    private Typeface GAME_FONT_LETTERS;
    private Typeface GAME_FONT_NUMBERS;

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
        //Intent intent = new Intent(this, LevelSelectionActivity.class);
        Intent intent = new Intent(this, StageActivity.class);
        intent.putExtra("GAME_MODE", gameMode);
        intent.putExtra("GAME", game);
        intent.putExtra("CURRENT_LEVEL",currentLevel);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        ButterKnife.bind(this);

        GAME_FONT_LETTERS = Typeface.createFromAsset(getAssets(), "CHERI___.TTF");
        GAME_FONT_NUMBERS = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");
        game = new Game(HomeScreen.dc);
        initProgress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_CODE == requestCode) {
            if(RESULT_CODE == resultCode) {
                //int gamePoints = data.getExtras().getInt("TOP_PLAYER");
                    //game.getTopPlayer().setGamePoints(gamePoints);
                int totalTime = data.getExtras().getInt("TIMER");
                    game.getTimer().setTotalTime(totalTime);
                gameMode = data.getExtras().getInt("GAME_MODE");
                switch(gameMode) {
                    case BaseGame.MODE_BEGINNER : game.setBeginnerGame((BaseGame)data.getExtras().getSerializable("GAME"));
                        break;
                    case BaseGame.MODE_ADVANCED : game.setAdvancedGame((BaseGame) data.getExtras().getSerializable("GAME"));
                        break;
                    case BaseGame.MODE_EXPERT : game.setExpertGame((BaseGame) data.getExtras().getSerializable("GAME"));
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

    @Override
    public void onBackPressed() {
        final boolean[] back = {false};
        AlertDialog ad = new AlertDialog.Builder(this)
                .setMessage("DO YOU WANT TO SAVE BEFORE GOING BACK?")
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        back[0] = true;
                        game.getTopPlayer().setGamePoints(game.getBeginnerGame().getAccomplished()+game.getAdvancedGame().getAccomplished()+game.getExpertGame().getAccomplished());
                        HomeScreen.dc.addTopPlayer(game.getTopPlayer());

                    }
                })
                .setCancelable(false)
                .create();
        ad.show();

        if(back[0])
            super.onBackPressed();
    }

    /*@Override
    public void finish() {
        AlertDialog ad = new AlertDialog.Builder(this)
                .setMessage("DO YOU WANT TO SAVE BEFORE GOING BACK?")
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game.getTopPlayer().setGamePoints(game.getBeginnerGame().getAccomplished()+game.getAdvancedGame().getAccomplished()+game.getExpertGame().getAccomplished());
                        HomeScreen.dc.addTopPlayer(game.getTopPlayer());
                    }
                })
                .setCancelable(false)
                .create();
        ad.show();
        super.finish();
    }*/

    private void initProgress() {
        beginner.setTypeface(GAME_FONT_NUMBERS);
        advanced.setTypeface(GAME_FONT_NUMBERS);
        expert.setTypeface(GAME_FONT_NUMBERS);

        beginner.setText((game.getBeginnerGame().getCurrentLevel())+"/"+game.getBeginnerGame().getQuestionsSize());
        advanced.setText((game.getAdvancedGame().getCurrentLevel())+"/"+game.getAdvancedGame().getQuestionsSize());
        expert.setText((game.getExpertGame().getCurrentLevel())+"/"+game.getExpertGame().getQuestionsSize());
    }
}
