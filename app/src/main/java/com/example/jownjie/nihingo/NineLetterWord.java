package com.example.jownjie.nihingo;

import android.annotation.TargetApi;
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
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.jownjie.nihingo.Models.Modes.BaseGame;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hebi5 on 1/24/2016.
 * //TODO: this fragment is for the 9 - letter word screen.
 */
public class NineLetterWord extends Fragment implements View.OnClickListener {
    @Bind(R.id.nine_tableLayout)
    TableLayout tableLayout;


    View rootView;

    private Typeface GAME_FONT_NUMBERS;
    Button[] levelBtnList;
    TableRow[] tbls;
    private int questionsSize=10;
    private String gameModeString;
    private int REQUEST_CODE = 0;

    @OnClick(R.id.nine_button_back_menu)
    public void back(){
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_9letterword, container, false);
        ButterKnife.bind(this, rootView);

        GAME_FONT_NUMBERS = Typeface.createFromAsset(getActivity().getAssets(), "KOMIKAX_.ttf");
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

    public NineLetterWord getNewInstance(String gameMode){
        NineLetterWord nineLetterWord = new NineLetterWord();
        Bundle bundle = new Bundle();
        bundle.putString("GAME_MODE", gameMode);
        gameModeString = gameMode;
        //Log.e("NineLetterWord", gameMode);

        nineLetterWord.setArguments(bundle);

        return nineLetterWord;
    }


    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        int level = Integer.parseInt(button.getText().toString());

        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("GAME_MODE", StageActivity.gameMode);
        intent.putExtra("GAME", StageActivity.CURRENT_GAME);
        intent.putExtra("GAME_DIFFICULTY", BaseGame.POOL_LONG);
        intent.putExtra("CURRENT_LEVEL",level-1);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
