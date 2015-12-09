package com.example.jownjie.nihingo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jownjie.nihingo.Database.DatabaseConnector;
import com.example.jownjie.nihingo.Models.GamePool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private String answer="";
    private Button[] buttons;
    private int wordCount=0;
    private String newAnswer;
    DatabaseConnector dc;
    GamePool temp;

    @Bind(R.id.ans_container)
    LinearLayout ansContainer;


    @Bind(R.id.imageView)
    ImageView imageView;

    @OnClick({R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button10})
    public void displayCharacters(Button button){
        //button.setVisibility(View.INVISIBLE);
        appendToContainer(button.getText().toString());
    }

    public void initAnswerContainer(int count){
        for(int i = 0; i < newAnswer.length(); i++){
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(this, null, android.R.attr.buttonStyleSmall);
            btn.setLayoutParams(btnparams);
            btn.setOnClickListener(this);
            ansContainer.addView(btn);

            buttons[i] = btn;
        }
    }

    public void appendToContainer(String letter){
        answer+=letter;
        for(int i =0;i<buttons.length;i++){
            if(buttons[i].getText().equals("")){
                buttons[i].setText(letter);
                break;
            }
        }
        if(answer.equals(newAnswer)){
            Toast.makeText(this, "congratulations dickface no one cares", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        dc = new DatabaseConnector(this,1);

        /***/
        newAnswer = "COCK";
        int newDr = R.drawable.advanced_coconut;
        String fileName = "advanced_coconut.png";
        /****/

        buttons = new Button[newAnswer.length()];
        initAnswerContainer(buttons.length);

        if(dc.insertGameLevel(new GamePool(null,newAnswer,"TESTHINT"),createFileFromResource(newDr,fileName)))
            Toast.makeText(this,"SUCCESS!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"FAILED!", Toast.LENGTH_SHORT).show();
        temp = dc.getGameLevel(newAnswer);
        if(temp!=null) {
            imageView.setImageBitmap(temp.getImageDr());
            Toast.makeText(this, String.valueOf(temp.getAnswer()), Toast.LENGTH_LONG);
        }
    }

    public File createFileFromResource(int resId, String fileName)
    {
        File f;
        try
        {
            f = new File(getCacheDir() + File.separator + fileName);
            InputStream is = getResources().openRawResource(resId);
            OutputStream out = new FileOutputStream(f);

            int bytesRead;
            byte[] buffer = new byte[1024];
            while((bytesRead = is.read(buffer)) > 0)
            {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            is.close();
        }
        catch (IOException ex)
        {
            f = null;
        }
        return f;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        button.setText("");
    }
}
