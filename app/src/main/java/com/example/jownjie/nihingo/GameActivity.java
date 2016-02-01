package com.example.jownjie.nihingo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jownjie.nihingo.Game.Random;
import com.example.jownjie.nihingo.Models.Game;
import com.example.jownjie.nihingo.Models.GamePool;
import com.example.jownjie.nihingo.Models.Modes.AdvancedGame;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;
import com.example.jownjie.nihingo.Models.Modes.BeginnerGame;
import com.example.jownjie.nihingo.Models.Modes.ExpertGame;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  edited by hebi5 on 12/10/15: added button functionality for choosing and removing letters
 */
public class GameActivity extends AppCompatActivity {


    private Typeface GAME_FONT_LETTERS;
    private Typeface GAME_FONT_NUMBERS;
    int a=0;
    private Button[] answerList;
    int[] buttonPosArr;

    //For Gameplay variables
    private int gameMode;
    private Game game = null;
    private BaseGame baseGame;
    private GamePool currentQuestion;
    private int RESULT_CODE = 1;

    @Bind({R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button10,
            R.id.button11,
            R.id.button12})
    List<Button> choiceList;


    @Bind(R.id.textViewTimer)
    TextView timer;

    @Bind(R.id.ans_container)
    LinearLayout ansContainer;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);


        GAME_FONT_LETTERS = Typeface.createFromAsset(getAssets(), "CHERI___.TTF");
        GAME_FONT_NUMBERS = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");
        initActivity();
    }

    /**
     * initializes
     * the container for
     * List<Button> choiceList
     * @param gameMode
     */

    public void initChoiceContainer(int gameMode){
        int a = (gameMode==2)?0:2;
        int size = choiceList.size()-a;
        String rand = Random.randomize(Random.completeWord(Random.randomize(currentQuestion.getAnswer().toUpperCase()), size));

        for (int i = 0; i < size; i++) {
            final int pos = i;
            choiceList.get(i).setVisibility(View.VISIBLE);
            choiceList.get(i).setTypeface(GAME_FONT_LETTERS);
            choiceList.get(i).setText(rand.charAt(i) + "");
            choiceList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!answerContainerFull()) {
                        choiceList.get(pos).setVisibility(View.INVISIBLE);
                        appendToContainer(pos);
                    }
                }
            });

        }
    }

    /**
     * initializes
     * container
     * for Button[] answerList
     */

    public void initAnswerContainer(int contSize){
        answerList = new Button[contSize];
        for(int i = 0; i < currentQuestion.getAnswer().length(); i++){
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            btnparams.setMargins(1,0,1,0);
            answerList[i] = new Button(this, null, android.R.attr.buttonStyleSmall);
            answerList[i].setTypeface(GAME_FONT_LETTERS);
            answerList[i].setText("");
            answerList[i].setLayoutParams(btnparams);
            final int pos = i;
            answerList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choiceList.get(buttonPosArr[pos]).setVisibility(View.VISIBLE);
                    answerList[pos].setText("");
                }
            });
            ansContainer.addView(answerList[i]);
        }
    }

    /**
     * append letter to
     * the answerList container
     * @param item, type int: position of appended letter from choiceList
     */
    public void appendToContainer(int item){
        String currentAnswer;
        for(int i =0;i< answerList.length;i++){
            if(answerList[i].getText().toString().equals("")){
                answerList[i].setText(choiceList.get(item).getText().toString());
                buttonPosArr[i]=item;
                a++;
                break;
            }
        }
        currentAnswer = getAnswer();
        if(currentQuestion.getAnswer().contentEquals(currentAnswer.toLowerCase())){
            game.getTimer().setPause(true);
            game.getTimer().setTotalTime(game.getTimer().getTotalTime()+game.getTimer().getTime());
            game.getTopPlayer().setGamePoints(game.getTopPlayer().getGamePoints() + baseGame.getPoints(game.getTimer().getTime(), game.getTimer().getTotalTime()));

            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage("SUCCESS")
                    .setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            newQuestion();
                        }
                    })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            game.getTimer().cancel(true);
                            GameActivity.this.finish();
                        }
                    })
                    .setNeutralButton("RETRY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GameActivity.this.recreate();
                        }
                    })
                    .setCancelable(false)
                    .create();
            ad.show();

        }
    }

    public boolean answerContainerFull(){
        boolean flag = true;
        for(int i =0;i< answerList.length;i++){
            if(answerList[i].getText().toString().equals("")){
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void initActivity() {
        try {
            gameMode = getIntent().getExtras().getInt("GAME_MODE");
            game = (Game)getIntent().getExtras().getSerializable("GAME");
            int currentLevel = getIntent().getExtras().getInt("CURRENT_LEVEL");
            switch(gameMode) {
                case BaseGame.MODE_BEGINNER : baseGame = new BeginnerGame();
                    baseGame.setGamePoolList(game.getBeginnerGame().getGamePoolList());
                    baseGame.setQuestionsSize(game.getBeginnerGame().getQuestionsSize());
                    break;
                case BaseGame.MODE_ADVANCED : baseGame = new AdvancedGame();
                    baseGame.setGamePoolList(game.getAdvancedGame().getGamePoolList());
                    baseGame.setQuestionsSize(game.getAdvancedGame().getQuestionsSize());
                    break;
                case BaseGame.MODE_EXPERT : baseGame = new ExpertGame();
                    baseGame.setGamePoolList(game.getExpertGame().getGamePoolList());
                    baseGame.setQuestionsSize(game.getExpertGame().getQuestionsSize());
                    break;
                case BaseGame.MODE_NULL : Log.e("ERROR", "UNIDENTIFIED GAME MODE!");
                    GameActivity.this.finish();
                    break;
            }
            baseGame.setCurrentLevel(currentLevel);
            newQuestion();
            game.newT(timer);
            game.getTimer().execute();
        } catch(NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    private void newQuestion() {
        if(!baseGame.isAccomplished()) {
            currentQuestion = baseGame.getNextLevel();
            if(currentQuestion!=null) {
                buttonPosArr = new int[currentQuestion.getAnswer().length()];
                initChoiceContainer(gameMode);
                ansContainer.removeAllViews();
                initAnswerContainer(currentQuestion.getAnswer().length());
                imageView.setImageResource(currentQuestion.getImageRes());
            }
        } else {
            game.getTimer().cancel(true);
            AlertDialog ad = new AlertDialog.Builder(this)
                    .setMessage("SUCCESSULLY ANSWERED ALL QUESTIONS!")
                    .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            GameActivity.this.finish();
                        }
                    })
                    .setNeutralButton("RETRY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GameActivity.this.recreate();
                        }
                    })
                    .setCancelable(false)
                    .create();
            ad.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        game.getTimer().cancel(true);

    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("GAME_MODE",gameMode);
        intent.putExtra("TOP_PLAYER", game.getTopPlayer().getGamePoints());
        intent.putExtra("CURRENT_LEVEL", baseGame.getCurrentLevel());
        intent.putExtra("GAME", baseGame);
        intent.putExtra("TIMER", game.getTimer().getTotalTime());
        this.setResult(RESULT_CODE,intent);
        super.finish();
    }

    private String getAnswer() {
        String answer = "";
        for(int i=0;i<answerList.length;i++) {
            answer += answerList[i].getText()+"";
        }
        return answer;
    }

}
