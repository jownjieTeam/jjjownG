package com.example.jownjie.nihingo.Database;

import android.content.Context;

import com.example.jownjie.nihingo.Models.GamePool;
import com.example.jownjie.nihingo.Models.TopPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/9/2015.
 */
public class DatabaseController implements Serializable {

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

    /*
     * inserts data (GamePool)
     * @param gp, type GamePool : object used to insert data
     * @return bool
     */
    public boolean insertGamePool(GamePool gp) {
        if(gp!=null)
            return dc.addGamePool(gp);
        else
            return false;
    }

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

    //helper method to check validity of gameMode
    private boolean validGameMode(int gameMode) {
        return gameMode == 0 || gameMode == 1 || gameMode == 2;
    }
}
