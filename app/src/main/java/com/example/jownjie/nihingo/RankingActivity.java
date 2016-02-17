package com.example.jownjie.nihingo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jownjie.nihingo.Models.TopPlayer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RankingActivity extends AppCompatActivity {
    @Bind(R.id.listView)ListView listView;
    @Bind(R.id.textView6)TextView tv;


    private Typeface GAME_FONT_LETTERS;
    private Typeface GAME_FONT_NUMBERS;

    ArrayList<String> topPlayerList = new ArrayList<>();
    ArrayList<String> topPointsList = new ArrayList<>();

    @OnClick(R.id.button_back_menu_rank)
    public void back(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);

        GAME_FONT_LETTERS = Typeface.createFromAsset(getAssets(), "CHERI___.TTF");
        GAME_FONT_NUMBERS = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");

        TopPlayer[] topPlayers = HomeScreen.dc.getTopPlayer();

        for(int i = 0; i < topPlayers.length; i++){
            if(topPlayers[i]!=null) {
                if(topPlayers[i].getPlayerName()!=null) {
                    topPlayerList.add(topPlayers[i].getPlayerName()+"");
                    topPointsList.add(topPlayers[i].getGamePoints()+"");
                }
            }
        }

        if(topPlayers.length>0) {
            RankingAdapter adapter = new RankingAdapter(this, topPlayerList, topPointsList);
            listView.setAdapter(adapter);
        }else{
            listView.setVisibility(View.GONE);
            tv.setTypeface(GAME_FONT_LETTERS);
            tv.setText("no players found!");
        }
    }

    static class ViewHolder{
        public TextView textViewPlayerName;
        public TextView textViewPlayerPoints;
        public TextView textViewRank;
    }

    class RankingAdapter extends BaseAdapter{
        private Context mContext;
        private ArrayList<String> mPlayerList;
        private ArrayList<String> mPointList;

        private Typeface GAME_FONT_LETTERS;
        private Typeface GAME_FONT_NUMBERS;

        public RankingAdapter(Context context, ArrayList<String> playerList, ArrayList<String> pointList){

            GAME_FONT_LETTERS = Typeface.createFromAsset(getAssets(), "CHERI___.TTF");
            GAME_FONT_NUMBERS = Typeface.createFromAsset(getAssets(), "KOMIKAX_.ttf");
            mContext = context;
            mPlayerList = playerList;
            mPointList = pointList;
        }

        @Override
        public int getCount() {
            return mPlayerList.size();
        }

        @Override
        public Object getItem(int position) {
            return mPlayerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rootView = convertView;
            ViewHolder mHolder;

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rootView = inflater.inflate(R.layout.ranking_list_item, parent, false);
                mHolder = new ViewHolder();
                mHolder.textViewRank = (TextView) rootView.findViewById(R.id.tv_rank);
                mHolder.textViewPlayerName = (TextView) rootView.findViewById(R.id.tv_player_name);
                mHolder.textViewPlayerPoints = (TextView) rootView.findViewById(R.id.tv_player_points);
                rootView.setTag(mHolder);
            }
            else{
                mHolder = (ViewHolder)rootView.getTag();
            }

            mHolder.textViewRank.setTypeface(GAME_FONT_NUMBERS);
            mHolder.textViewPlayerName.setTypeface(GAME_FONT_LETTERS);
            mHolder.textViewPlayerPoints.setTypeface(GAME_FONT_NUMBERS);

            switch(position){
                case 0:
                    mHolder.textViewRank.setTextSize(50);break;
                case 1:
                    mHolder.textViewRank.setTextSize(40);break;
                case 2:
                    mHolder.textViewRank.setTextSize(30);break;
                default:
                    mHolder.textViewRank.setTextSize(20);break;

            }
            mHolder.textViewRank.setText((position+1)+"");

            mHolder.textViewPlayerName.setText(mPlayerList.get(position));
            mHolder.textViewPlayerPoints.setText(mPointList.get(position));


            Log.e("asd", "done");
            return rootView;
        }
    }
}
