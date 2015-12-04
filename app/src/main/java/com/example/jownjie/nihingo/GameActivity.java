package com.example.jownjie.nihingo;

import android.os.Bundle;
import android.support.annotation.RawRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jownjie.nihingo.Database.DatabaseConnector;
import com.example.jownjie.nihingo.Models.GameLevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {
    DatabaseConnector dc;
    GameLevel temp;

    @Bind(R.id.textView)
    TextView textView;

    @Bind(R.id.imageView)
    ImageView imageView;

    @OnClick({R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7})
    public void displayCharacters(Button button){
        textView.append(button.getText().toString());
        //textView.setText(button.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        dc = new DatabaseConnector(this,1);

        if(dc.insertGameLevel(new GameLevel(null,false,2,"TEST"),createFileFromResource(R.drawable.test,"test.png")))
            Toast.makeText(this,"SUCCESS!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"FAILED!", Toast.LENGTH_SHORT).show();
        temp = dc.getGameLevel(this,"2");
        if(temp!=null) {
            imageView.setImageBitmap(temp.getImageDr());
            Toast.makeText(this,String.valueOf(temp.getAnswer()),Toast.LENGTH_LONG);
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
}
