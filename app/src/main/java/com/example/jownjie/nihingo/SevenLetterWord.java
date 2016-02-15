package com.example.jownjie.nihingo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.jownjie.nihingo.Models.Modes.BaseGame;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hebi5 on 1/24/2016.
 * //TODO: this fragment is for the 7 - letter word screen.
 */
public class SevenLetterWord extends Fragment implements View.OnClickListener {
    @Bind(R.id.seven_tableLayout)
    TableLayout tableLayout;


    View rootView;

    private Typeface GAME_FONT_NUMBERS;
    TableRow[] tbls;
    private int questionsSize=10;
    private String gameModeString;
    private int REQUEST_CODE = 0;

    private List<Integer> levelsAccomplishedList;

    @Bind({R.id.seven_button1,
            R.id.seven_button2,
            R.id.seven_button3,
            R.id.seven_button4,
            R.id.seven_button5,
            R.id.seven_button6,
            R.id.seven_button7,
            R.id.seven_button8,
            R.id.seven_button9,
            R.id.seven_button10})
    Button[] levelBtnList;

    @OnClick(R.id.seven_button_back_menu)
    public void back(){
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_7letterword, container, false);
        ButterKnife.bind(this, rootView);

        switch(StageActivity.gameMode){
            case BaseGame.MODE_BEGINNER:
                levelsAccomplishedList = StageActivity.CURRENT_GAME.getBeginnerGame().getLevelsAccomplished(BaseGame.POOL_MEDIUM);
                for(int i=0; i < levelsAccomplishedList.size(); i++) {
                    if (levelBtnList[levelsAccomplishedList.get(i) - 1].getBackground() != this.getResources().getDrawable(R.mipmap.star_box))
                        levelBtnList[levelsAccomplishedList.get(i) - 1].setBackground(this.getResources().getDrawable(R.mipmap.star_box));
                }
                break;
            case BaseGame.MODE_ADVANCED:
                levelsAccomplishedList = StageActivity.CURRENT_GAME.getAdvancedGame().getLevelsAccomplished(BaseGame.POOL_MEDIUM);
                for(int i=0; i < levelsAccomplishedList.size(); i++) {
                    if(levelBtnList[levelsAccomplishedList.get(i)-1].getBackground()!=this.getResources().getDrawable(R.mipmap.star_box))
                        levelBtnList[levelsAccomplishedList.get(i)-1].setBackground(this.getResources().getDrawable(R.mipmap.star_box));
                }
                break;
            case BaseGame.MODE_EXPERT:
                levelsAccomplishedList = StageActivity.CURRENT_GAME.getExpertGame().getLevelsAccomplished(BaseGame.POOL_MEDIUM);
                for(int i=0; i < levelsAccomplishedList.size(); i++) {
                    if(levelBtnList[levelsAccomplishedList.get(i)-1].getBackground() != this.getResources().getDrawable(R.mipmap.star_box))
                        levelBtnList[levelsAccomplishedList.get(i)-1].setBackground(this.getResources().getDrawable(R.mipmap.star_box));
                }
                break;

        }

        GAME_FONT_NUMBERS = Typeface.createFromAsset(getActivity().getAssets(), "KOMIKAX_.ttf");
        initLevelBtnList();
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    //because you cannot set typeface in xml report AS
    public void initLevelBtnList(){
        for(int i = 0; i < levelBtnList.length; i++){
            levelBtnList[i].setTypeface(GAME_FONT_NUMBERS);
            levelBtnList[i].setText(i+1+"");
            levelBtnList[i].setOnClickListener(this);
        }
    }

    public Button[] getLevelBtnList() {
        return levelBtnList;
    }
    public SevenLetterWord getNewInstance(String gameMode){
        SevenLetterWord sevenLetterWord = new SevenLetterWord();
        Bundle bundle = new Bundle();
        bundle.putString("GAME_MODE", gameMode);
        gameModeString = gameMode;
        //Log.e("SevenLetterWord", gameMode);

        sevenLetterWord.setArguments(bundle);

        return sevenLetterWord;
    }


    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        int level = Integer.parseInt(button.getText().toString());

        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("GAME_MODE", StageActivity.gameMode);
        intent.putExtra("GAME", StageActivity.CURRENT_GAME);
        intent.putExtra("GAME_DIFFICULTY", BaseGame.POOL_MEDIUM);
        intent.putExtra("CURRENT_LEVEL",level-1);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
