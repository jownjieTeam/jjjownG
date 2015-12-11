package com.example.jownjie.nihingo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jownjie.nihingo.Database.DatabaseController;
import com.example.jownjie.nihingo.Models.GamePool;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *  edited by hebi5 on 12/10/15: added button functionality for choosing and removing letters
 */
public class GameActivity extends AppCompatActivity{
    String answer = "";
    int a=0;
    private Button[] answerList;
    int[] buttonPosArr;
    private String newAnswer;
    DatabaseController dc;
    GamePool temp;

    @Bind({R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button10})
    List<Button> choiceList;

    @Bind(R.id.ans_container)
    LinearLayout ansContainer;

    @Bind(R.id.imageView)
    ImageView imageView;

    /**
     * initializes
     * the container for
     * List<Button> choiceList
     */

    public void initChoiceContainer(){
        for(int i = 0; i< choiceList.size();i++){
            final int pos = i;
            choiceList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(a!=newAnswer.length()) {
                        choiceList.get(pos).setVisibility(View.INVISIBLE);
                        appendToContainer(pos);
                    }
                }
            });
        }
    }

    /**
     * initializes
     * container
     * for Button[] answerList
     */

    public void initAnswerContainer(){
        for(int i = 0; i < newAnswer.length(); i++){
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(60, 60);
            btnparams.setMarginEnd(1);
            answerList[i] = new Button(this, null, android.R.attr.buttonStyleSmall);
            answerList[i].setLayoutParams(btnparams);
            final int pos = i;
            answerList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a--;
                    choiceList.get(buttonPosArr[pos]).setVisibility(View.VISIBLE);
                    answerList[pos].setText("");
                }
            });
            ansContainer.addView(answerList[i]);
        }
    }

    /**
     * append letter to
     * the answerList container
     * @param item, type int: position of appended letter from choiceList
     */

    public void appendToContainer(int item){
        //answerList+=letter;
        for(int i =0;i< answerList.length;i++){
            if(answerList[i].getText().toString().equals("")){
                answerList[i].setText(choiceList.get(item).getText().toString());
                buttonPosArr[i]=item;
                a++;
                break;
            }
        }
    }

    public void answerContainerFull(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        dc = new DatabaseController(this,1);

        /***/
        newAnswer = "barbecue";
        int newDr = R.drawable.expert_barbecue;
        String fileName = "expert_barbecue.png";
        /****/

        //TODO: asdasd ------------
        initChoiceContainer();

        //TODO: asdasd ------------

        answerList = new Button[newAnswer.length()];
        buttonPosArr = new int[newAnswer.length()];
        initAnswerContainer();

        if(dc.insertGamePool(new GamePool(newDr, fileName, "TESTHINT")))
            Toast.makeText(this,"SUCCESS!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"FAILED!", Toast.LENGTH_SHORT).show();
        temp = dc.getGamePool(newAnswer);
        if(temp!=null) {
            imageView.setImageResource(temp.getImageRes());
            Toast.makeText(this, String.valueOf(temp.getImageRes()) + " AND " + newDr + " AND " + temp.getHint(), Toast.LENGTH_LONG).show();
        }

    }

    /*
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
    */

}
