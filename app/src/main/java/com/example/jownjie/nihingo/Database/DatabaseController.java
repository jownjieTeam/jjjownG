package com.example.jownjie.nihingo.Database;

import android.content.Context;

import com.example.jownjie.nihingo.Models.GamePool;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;
import com.example.jownjie.nihingo.Models.TopPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/9/2015.
 */
public class DatabaseController {

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
     * adds topPlayer to database (if included in top 10)
     * @param topPlayer, type TopPlayer : object to be inserted into database.
     * @return bool
     */
    public boolean addTopPlayer(TopPlayer tp) {
        boolean ok = false;
        if(tp!=null) {
            ok = dc.addTopPlayer(tp);
        }
        return ok;
    }

    /*
     * retrieves data (TopPlayer)
     * @param gameMode, type int : used as condition in query
     * @return TopPlayer[]
     */
    public TopPlayer[] getTopPlayer() {
        TopPlayer[] topPlayerArray = new TopPlayer[10];
        topPlayerArray = dc.getTopPlayer();
        return topPlayerArray;
    }

    /*
     * retrieves data (TopPlayer)
     * @param palyerName, type string : used as condition in query
     * @return TopPlayer
     */
    public TopPlayer getTopPlayer(String playerName) {
        TopPlayer tp = dc.getTopPlayer(playerName);
        return tp;
    }

    //helper method to check validity of gameMode
    private boolean validGameMode(int gameMode) {
        return gameMode == BaseGame.MODE_BEGINNER || gameMode == BaseGame.MODE_ADVANCED || gameMode == BaseGame.MODE_EXPERT;
    }
}
