package com.example.jownjie.nihingo.Game;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by hebi5 on 12/12/2015.
 */
public class Timer extends AsyncTask<Void, Integer, Void> {
    private TextView tvTimer;
    private int time = 0;

    public Timer(TextView tvTimer){
        this.tvTimer = tvTimer;
        Log.e("wtf", "timer staato!");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        tvTimer.setText(values[0] + "");
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
}
