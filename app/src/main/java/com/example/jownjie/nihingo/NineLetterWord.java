package com.example.jownjie.nihingo;

import android.annotation.TargetApi;
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

    Button[] levelBtnList;
    TableRow[] tbls;
    private int questionsSize=5;
    private String gameModeString;

    @OnClick(R.id.nine_button_back_menu)
    public void back(){
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_9letterword, container, false);
        ButterKnife.bind(this, rootView);

        inflateLevels();
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void inflateLevels(){
        int ctr = 1;
        tbls = new TableRow[(questionsSize%2==0)?questionsSize/5:(questionsSize/5)+1];
        levelBtnList = new Button[questionsSize];
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(100, 100);

        for(int i = 0; i < tbls.length; i++) {
            tbls[i] = new TableRow(getActivity());
            tbls[i].setGravity(Gravity.CENTER);
            tableLayout.addView(tbls[i]);
            for(int j = 0; j < 3 && ctr <= questionsSize; j++) {
                levelBtnList[i] = new Button(getActivity(), null, android.R.attr.buttonStyleSmall);
                levelBtnList[i].setLayoutParams(layoutParams);
                levelBtnList[i].setBackground(getResources().getDrawable(R.drawable.box));
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
        Log.e("NineLetterWord", gameMode);

        nineLetterWord.setArguments(bundle);

        return nineLetterWord;
    }


    @Override
    public void onClick(View v) {
    }
}
