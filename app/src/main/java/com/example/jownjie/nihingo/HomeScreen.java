package com.example.jownjie.nihingo;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jownjie.nihingo.Database.DatabaseController;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeScreen extends AppCompatActivity {

    public static DatabaseController dc;

    @OnClick(R.id.button_play)
    public void playGame(){
        //TODO: diri tong new play game2 shit wtf bro
        startActivity(new Intent(this, ModeActivity.class));
    }

    @OnClick(R.id.button_instructions)
     public void instructions(){
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new InstructionsFragment(), "INSTRUCTIONS_FRAGMENT").commit();*/

        startActivity(new Intent(this, StageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
        dc = new DatabaseController(this,1);
    }
}
