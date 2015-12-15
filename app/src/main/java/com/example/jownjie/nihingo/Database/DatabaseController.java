package com.example.jownjie.nihingo.Database;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import com.example.jownjie.nihingo.Models.GamePool;
import com.example.jownjie.nihingo.Models.TopPlayer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/9/2015.
 */
public class DatabaseController implements Parcelable {

    //private DatabaseConnector variable
    private DatabaseConnector dc;

    /*
     * Constructor for DatabaseConnector
     * @param context, type Context : see DatabaseConnector constructor.
     * @param version, type int : see DatabaseConnector constructor.
     */
    public DatabaseController(Context context,int version) {
        dc = new DatabaseConnector(context,version);
    }

    protected DatabaseController(Parcel in) {
        String[] passedData = new String[1];
        in.readStringArray(passedData);
    }

    public static final Creator<DatabaseController> CREATOR = new Creator<DatabaseController>() {
        @Override
        public DatabaseController createFromParcel(Parcel in) {
            return new DatabaseController(in);
        }

        @Override
        public DatabaseController[] newArray(int size) {
            return new DatabaseController[size];
        }
    };

    /*
         * retrieves data (GamePool)
         * @param answer, type String : used as condition in query
         * @return GamePool
         */
    public GamePool getGamePool(String answer) {
        if(!answer.isEmpty())
            return dc.getGamePool(answer);
        else
            return null;
    }

    /*
     * retrieves data (GamePool)
     * @param gameMode, type int : used as condition in query
     * @return List<GamePool>
     */
    public List<GamePool> getGamePool(int gameMode) {
        List<GamePool> gamePoolList = new ArrayList<GamePool>();
        if(validGameMode(gameMode))
            gamePoolList = dc.getGamePool(gameMode);
        return gamePoolList;
    }

    /*
     * retrieves data (TopPlayer)
     * @param gameMode, type int : used as condition in query
     * @return TopPlayer[]
     */
    public TopPlayer[] getTopPlayer(int gameMode) {
        TopPlayer[] topPlayerArray = new TopPlayer[10];
        if(validGameMode(gameMode))
            topPlayerArray = dc.getTopPlayer(gameMode);
        return topPlayerArray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    //helper method to check validity of gameMode
    private boolean validGameMode(int gameMode) {
        return gameMode == 0 || gameMode == 1 || gameMode == 2;
    }
}
