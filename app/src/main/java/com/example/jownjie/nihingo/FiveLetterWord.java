package com.example.jownjie.nihingo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.jownjie.nihingo.Models.Modes.BaseGame;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hebi5 on 1/24/2016.
 * //TODO: this fragment is for the 5 - letter word screen.
 */
public class FiveLetterWord extends Fragment implements View.OnClickListener{
    @Bind(R.id.five_tableLayout)
    TableLayout tableLayout;

    private Typeface GAME_FONT_NUMBERS;
    View rootView;
    FiveLetterWord mFiveLetterWord;
    private int REQUEST_CODE = 0;

    public static TableRow[] tbls;
    private int questionsSize;
    private String gameModeString;

    private List<Integer> levelsAccomplishedList;

    @Bind({R.id.five_button1,
            R.id.five_button2,
            R.id.five_button3,
            R.id.five_button4,
            R.id.five_button5,
            R.id.five_button6,
            R.id.five_button7,
            R.id.five_button8,
            R.id.five_button9,
            R.id.five_button10})
    Button[] levelBtnList;

    @OnClick(R.id.five_button_back_menu)
    public void back(){
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_5letterword, container, false);
        ButterKnife.bind(this, rootView);

        switch(StageActivity.gameMode){
            case BaseGame.MODE_BEGINNER:
                levelsAccomplishedList = StageActivity.CURRENT_GAME.getBeginnerGame().getLevelsAccomplished(BaseGame.POOL_SHORT);
                for(int i=0; i < levelsAccomplishedList.size(); i++) {
                    if (levelBtnList[levelsAccomplishedList.get(i) - 1].getBackground() != this.getResources().getDrawable(R.mipmap.star_box))
                        levelBtnList[levelsAccomplishedList.get(i) - 1].setBackground(this.getResources().getDrawable(R.mipmap.star_box));
                }
                break;
            case BaseGame.MODE_ADVANCED:
                levelsAccomplishedList = StageActivity.CURRENT_GAME.getAdvancedGame().getLevelsAccomplished(BaseGame.POOL_SHORT);
                for(int i=0; i < levelsAccomplishedList.size(); i++) {
                    if(levelBtnList[levelsAccomplishedList.get(i)-1].getBackground()!=this.getResources().getDrawable(R.mipmap.star_box))
                        levelBtnList[levelsAccomplishedList.get(i)-1].setBackground(this.getResources().getDrawable(R.mipmap.star_box));
                }
                break;
            case BaseGame.MODE_EXPERT:
                levelsAccomplishedList = StageActivity.CURRENT_GAME.getExpertGame().getLevelsAccomplished(BaseGame.POOL_SHORT);
                for(int i=0; i < levelsAccomplishedList.size(); i++) {
                    if(levelBtnList[levelsAccomplishedList.get(i)-1].getBackground() != this.getResources().getDrawable(R.mipmap.star_box))
                        levelBtnList[levelsAccomplishedList.get(i)-1].setBackground(this.getResources().getDrawable(R.mipmap.star_box));
                }
                break;

        }

        // temporary
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

    public FiveLetterWord getNewInstance(String gameMode){
        FiveLetterWord fiveLetterWord = new FiveLetterWord();
        Bundle bundle = new Bundle();
        bundle.putString("GAME_MODE", gameMode);
        gameModeString = gameMode;
        //Log.e("FiveLetterWord", gameMode);

        fiveLetterWord.setArguments(bundle);

        return fiveLetterWord;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        int level = Integer.parseInt(button.getText().toString());

        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("GAME_MODE", StageActivity.gameMode);
        intent.putExtra("GAME", StageActivity.CURRENT_GAME);
        intent.putExtra("GAME_DIFFICULTY", BaseGame.POOL_SHORT);
        intent.putExtra("CURRENT_LEVEL",level-1);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
