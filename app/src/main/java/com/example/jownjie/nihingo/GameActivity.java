package com.example.jownjie.nihingo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private int REQUEST_CODE = 0;
    private int RESULT_CODE = 1;

    int a=0;
    private Button[] answerList;
    int[] buttonPosArr;

    //For Gameplay variables
    private int gameMode, gameDifficulty;
    private Game game = null;
    private BaseGame baseGame;
    private GamePool currentQuestion;

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

    @Bind(R.id.page1)LinearLayout gamePage;
    @Bind(R.id.page2)RelativeLayout successPage;
    @Bind(R.id.tv_excellent)TextView textNumber;
    @Bind(R.id.tv_successText)TextView textSuccess;
    @Bind(R.id.textView8)TextView textView8;

    @Bind(R.id.imageView2)ImageView star1;
    @Bind(R.id.imageView3)ImageView star2;
    @Bind(R.id.imageView4)ImageView star3;

    @OnClick(R.id.button_next_level_succ)
    public void nextLevel(){
        successPage.setVisibility(View.GONE);
        newQuestion();
    }

    @OnClick(R.id.button_back_menu_succ)
    public void back(){
        onBackPressed();
    }

    @OnClick(R.id.button_playSound)
    public void playSound(){
        if(HomeScreen.sound) {
            if (currentQuestion.getSoundRes() != 0) {
                MediaPlayer mp = MediaPlayer.create(this, currentQuestion.getSoundRes());
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.start();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);


        GAME_FONT_LETTERS = Typeface.createFromAsset(getAssets(), "CHERI___.TTF");
        GAME_FONT_NUMBERS = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");
        timer.setTypeface(GAME_FONT_NUMBERS);
        initActivity();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        nextLevel();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * initializes
     * the container for
     * List<Button> choiceList
     * @param gameMode
     */



    public void initChoiceContainer(int gameMode){
        int a = (gameMode==2)?0:2;
        int size = choiceList.size()-2;
        String rand = Random.randomize(Random.completeWord(Random.randomize(currentQuestion.getAnswer().toUpperCase()), size));
        for (int i = 0; i < size; i++) {
            final int pos = i;
            choiceList.get(i).setVisibility(View.VISIBLE);
            choiceList.get(i).setBackground(getResources().getDrawable(R.mipmap.box));
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initAnswerContainer(int contSize){
        answerList = new Button[contSize];
        for(int i = 0; i < currentQuestion.getAnswer().length(); i++){
            //LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(80, 80);
            answerList[i] = new Button(this, null, android.R.attr.buttonStyleSmall);
            answerList[i].setBackground(getResources().getDrawable(R.drawable.border_1));
            answerList[i].setTypeface(GAME_FONT_LETTERS);
            answerList[i].setTextSize(15);
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
            if(HomeScreen.sound) {
                MediaPlayer successPlayer = MediaPlayer.create(this, R.raw.success_sound);
                successPlayer.start();
            }

            game.getTimer().setPause(true);
            game.getTimer().setTotalTime(game.getTimer().getTotalTime() + game.getTimer().getTime());
            //game.getTopPlayer().setGamePoints(game.getTopPlayer().getGamePoints() + baseGame.getPoints(game.getTimer().getTime(), game.getTimer().getTotalTime()));
            baseGame.getCurrentQuestion().setAnswered(game.getTimer().getTime() < 10);

            //startActivityForResult(new Intent(this, SuccessActivity.class), REQUEST_CODE);
            successPage.setVisibility(View.VISIBLE);
            textNumber.setTypeface(GAME_FONT_NUMBERS);
            textView8.setTypeface(GAME_FONT_NUMBERS);
            textSuccess.setTypeface(GAME_FONT_NUMBERS);

            textSuccess.setText((baseGame.getCurrentQuestion().isAnswered()) ? "Excellent!" : "Success");
            textNumber.setText("level in " + game.getTimer().getTime() + " seconds");

            if(baseGame.getCurrentQuestion().isAnswered()){
                Animation a = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
                a.setDuration(500);

                star1.setVisibility(View.VISIBLE);
                star1.setAnimation(a);
                a.start();

                star2.setVisibility(View.VISIBLE);
                star2.setAnimation(a);
                a.start();

                star3.setVisibility(View.VISIBLE);
                star3.setAnimation(a);
                a.start();


            }
            else{
                star1.setVisibility(View.GONE);
                star2.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
            }
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
            gameDifficulty = getIntent().getExtras().getInt("GAME_DIFFICULTY");
            switch(gameMode) {
                case BaseGame.MODE_BEGINNER : baseGame = new BeginnerGame();
                    switch(gameDifficulty) {
                        case BaseGame.POOL_SHORT : baseGame.setGamePoolList(game.getBeginnerGame().getGameQuestions_SHORT());
                            break;
                        case BaseGame.POOL_MEDIUM : baseGame.setGamePoolList(game.getBeginnerGame().getGameQuestions_MEDIUM());
                            break;
                        case BaseGame.POOL_LONG : baseGame.setGamePoolList(game.getBeginnerGame().getGameQuestions_LONG());
                            break;
                        default: baseGame.setGamePoolList(null);
                            break;
                    }
                    break;
                case BaseGame.MODE_ADVANCED : baseGame = new AdvancedGame();
                    switch(gameDifficulty) {
                        case BaseGame.POOL_SHORT : baseGame.setGamePoolList(game.getAdvancedGame().getGameQuestions_SHORT());
                            break;
                        case BaseGame.POOL_MEDIUM : baseGame.setGamePoolList(game.getAdvancedGame().getGameQuestions_MEDIUM());
                            break;
                        case BaseGame.POOL_LONG : baseGame.setGamePoolList(game.getAdvancedGame().getGameQuestions_LONG());
                            break;
                        default: baseGame.setGamePoolList(null);
                            break;
                    }
                    break;
                case BaseGame.MODE_EXPERT : baseGame = new ExpertGame();
                    switch(gameDifficulty) {
                        case BaseGame.POOL_SHORT : baseGame.setGamePoolList(game.getExpertGame().getGameQuestions_SHORT());
                            break;
                        case BaseGame.POOL_MEDIUM : baseGame.setGamePoolList(game.getExpertGame().getGameQuestions_MEDIUM());
                            break;
                        case BaseGame.POOL_LONG : baseGame.setGamePoolList(game.getExpertGame().getGameQuestions_LONG());
                            break;
                        default: baseGame.setGamePoolList(null);
                            break;
                    }
                    break;
                case BaseGame.MODE_NULL : Log.e("ERROR", "UNIDENTIFIED GAME MODE!");
                    GameActivity.this.finish();
                    break;
            }
            baseGame.setQuestionsSize(baseGame.getGamePoolList().size());
            baseGame.setCurrentLevel(currentLevel);
            game.newT(timer);
            game.getTimer().setPause(true);
            game.getTimer().execute();
            newQuestion();
        } catch(NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    private void newQuestion() {

        if(!baseGame.isAccomplished()) {
            currentQuestion = baseGame.getNextLevel();
            if(currentQuestion!=null) {
                game.getTimer().setTheTimer(0);
                buttonPosArr = new int[currentQuestion.getAnswer().length()];
                initChoiceContainer(gameMode);
                ansContainer.removeAllViews();
                initAnswerContainer(currentQuestion.getAnswer().length());
                imageView.setImageResource(currentQuestion.getImageRes());
                game.getTimer().setPause(false);
            }
        } else {
            game.getTimer().cancel(true);
            GameActivity.this.finish();
            /*AlertDialog ad = new AlertDialog.Builder(this)
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
            ad.show();*/
        }
    }

    @Override
    public void onBackPressed() {
        game.getTimer().setPause(true);
        game.getTimer().cancel(true);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("GAME_MODE",gameMode);
        //intent.putExtra("TOP_PLAYER", game.getTopPlayer().getGamePoints());
        intent.putExtra("GAME", baseGame);
        intent.putExtra("TIMER", game.getTimer().getTotalTime());
        intent.putExtra("GAME_DIFFICULTY", gameDifficulty);
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
