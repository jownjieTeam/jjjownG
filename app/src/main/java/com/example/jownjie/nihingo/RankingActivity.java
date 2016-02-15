package com.example.jownjie.nihingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jownjie.nihingo.Models.TopPlayer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RankingActivity extends AppCompatActivity {
    @Bind(R.id.listView)ListView listView;

    ArrayList<String> topPlayerList = new ArrayList<>();

    @OnClick(R.id.button_back_menu_rank)
    public void back(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);

        TopPlayer[] topPlayers = HomeScreen.dc.getTopPlayer();

        for(int i = 0; i < topPlayers.length; i++){
            if(topPlayers[i]!=null) {
                if(topPlayers[i].getPlayerName()!=null) {
                    topPlayerList.add("Player: "+topPlayers[i].getPlayerName() + " Points: " + topPlayers[i].getGamePoints());
                }
            }
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topPlayerList));
    }
}
