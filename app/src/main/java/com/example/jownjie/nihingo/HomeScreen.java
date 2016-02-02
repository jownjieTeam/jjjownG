package com.example.jownjie.nihingo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jownjie.nihingo.Database.DatabaseController;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeScreen extends Activity {

    public static DatabaseController dc;

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
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        dc = new DatabaseController(this,1);
    }

    public void addFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
