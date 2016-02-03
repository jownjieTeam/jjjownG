package com.example.jownjie.nihingo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.jownjie.nihingo.Models.Modes.BaseGame;

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

    Button[] levelBtnList;
    TableRow[] tbls;
    private int questionsSize;
    private String gameModeString;

    @OnClick(R.id.five_button_back_menu)
    public void back(){
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_5letterword, container, false);
        ButterKnife.bind(this, rootView);

        // temporary
        GAME_FONT_NUMBERS = Typeface.createFromAsset(getActivity().getAssets(), "KOMIKAX_.ttf");
        questionsSize = 10;
        inflateLevels();
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void inflateLevels(){
        int columnNum = 5;
        int ctr = 1;
        tbls = new TableRow[(questionsSize%2==0)?questionsSize/columnNum:(questionsSize/columnNum)+1];
        levelBtnList = new Button[questionsSize];
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(100, 100);

        for(int i = 0; i < tbls.length; i++) {
            tbls[i] = new TableRow(getActivity());
            tbls[i].setGravity(Gravity.CENTER);
            tableLayout.addView(tbls[i]);
            for(int j = 0; j < columnNum && ctr <= questionsSize; j++) {
                levelBtnList[i] = new Button(getActivity(), null, android.R.attr.buttonStyleSmall);
                levelBtnList[i].setLayoutParams(layoutParams);
                levelBtnList[i].setBackground(getResources().getDrawable(R.drawable.box));
                levelBtnList[i].setTypeface(GAME_FONT_NUMBERS);
                levelBtnList[i].setText(ctr + "");
                levelBtnList[i].setOnClickListener(this);
                tbls[i].addView(levelBtnList[i]);
                ctr++;
            }
        }
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
