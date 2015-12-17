package com.example.jownjie.nihingo.Game;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by hebi5 on 12/12/2015.
 */
public class Timer extends AsyncTask<Void, Integer, Void> implements Serializable {
    private TextView tvTimer;
    private int time = 0;
    private int totalTime = 0;

    public Timer(TextView tvTimer){
        this.tvTimer = tvTimer;
        Log.e("wtf", "timer staato!");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        tvTimer.setText(setTime(values[0]));
    }

    private String setTime(int value) {
        int minutes = value/60;
        int seconds = value-(minutes*60);
        if(seconds>9)
            return "0"+minutes+":"+seconds;
        else
            return "0"+minutes+":0"+seconds;
    }

    @Override
    protected Void doInBackground(Void... params) {
        while(true){
            try {
                Thread.sleep(1000);
                time++;
                publishProgress(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isCancelled())break;

        }
        return null;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
