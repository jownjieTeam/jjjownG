package com.example.jownjie.nihingo;

import android.app.ActionBar;
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


    View rootView;

    Button[] levelBtnList;
    TableRow[] tbls;
    private int questionsSize=5;
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

        inflateLevels();
        return rootView;
    }

    public void inflateLevels(){
        int ctr = 1;
        tbls = new TableRow[(questionsSize%2==0)?questionsSize/3:(questionsSize/3)+1];
        levelBtnList = new Button[questionsSize];

        for(int i = 0; i < tbls.length; i++) {
            tbls[i] = new TableRow(getActivity());
            tbls[i].setGravity(Gravity.CENTER);
            tableLayout.addView(tbls[i]);
            for(int j = 0; j < 3 && ctr <= questionsSize; j++) {
                levelBtnList[i] = new Button(getActivity(), null, android.R.attr.buttonStyleSmall);
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
        Log.e("FiveLetterWord", gameMode);

        fiveLetterWord.setArguments(bundle);

        return fiveLetterWord;
    }

    @Override
    public void onClick(View v) {
    }
}
