package com.example.jownjie.nihingo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeScreen extends Activity {

    public static DatabaseController dc;
    public Stack<Fragment> fragmentStack;
    public static HomeScreen instance;
    public boolean volume = false;

    @OnClick(R.id.button_play)
    public void playGame(){
        //TODO: diri tong new play game2 shit wtf bro
        startActivity(new Intent(this, ModeActivity.class));
    }

    @OnClick(R.id.button_instructions)
     public void instructions(){
        addFragment(new InstructionsFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        instance = this;

        fragmentStack = new Stack();
        dc = new DatabaseController(this,1);
    }

    public static HomeScreen getInstance() {
        return instance;
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
