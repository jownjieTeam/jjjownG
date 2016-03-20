package com.example.jownjie.nihingo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jownjie.nihingo.Database.DatabaseController;

import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeScreen extends Activity {

    public static DatabaseController dc;
    public Stack<Fragment> fragmentStack;
    public static HomeScreen instance;
    public static Boolean sound = new Boolean(true);
    public boolean volume = false;

    @OnClick(R.id.button_play)
    public void playGame(){
        startActivity(new Intent(this, ModeActivity.class));
        //startActivity(new Intent(this, RankingActivity.class));
    }

    @OnClick(R.id.button_exit)
    public void exitGame(){
        HomeScreen.this.finish();
    }

    @OnClick(R.id.volume_btn)
    public void toggleMusic(ImageView view){
        if(sound){
            view.setBackground(getResources().getDrawable(R.mipmap.volume_down));
            sound = false;
        }
        else{
            view.setBackground(getResources().getDrawable(R.mipmap.volume_up));
            sound = true;
        }
    }

    @OnClick(R.id.button_instructions)
     public void instructions(){
        addFragment(new InstructionsFragment());
    }

    @OnClick(R.id.ranking_btn)
    public void ranking(){ startActivity(new Intent(this, RankingActivity.class));}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        instance = this;



        fragmentStack = new Stack();
        dc = new DatabaseController(this,1);
    }

    public static HomeScreen getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        if(!fragmentStack.empty()){
            removeTopFragment();
        }
        else{
            super.onBackPressed();
        }
    }

    public void removeTopFragment(){
        getFragmentManager()
                .beginTransaction()
                .remove(fragmentStack.pop())
                .commit();
    }

    public void addFragment(Fragment fragment){
        fragmentStack.push(fragment);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    public Stack<Fragment> getFragmentStack() {
        return fragmentStack;
    }
}
