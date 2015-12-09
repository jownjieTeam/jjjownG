package com.example.jownjie.nihingo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jownjie.nihingo.Models.GamePool;
import com.example.jownjie.nihingo.Models.TopPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/2/2015.
 */
public class DatabaseConnector {

    //DATABASE NAME
    public static final String DATABASE_NAME = "UNOFFICIAL_SPELLIT";

    //GAMEPOOL TABLE VARIABLES
    public static final String DATA_GAMEPOOL_NAME = "GamePool";
    public static final String DATA_GAMEPOOL_IMAGERES = "imageRes";
    public static final String DATA_GAMEPOOL_ANSWER = "answer";
    public static final String DATA_GAMEPOOL_HINT = "hint";
    public static final String DATA_GAMEPOOL_GAMEMODE = "gameMode";

    //TOPPLAYER TABLE VARIABLES
    public static final String DATA_TOPPLAYERS_NAME = "TopPlayers";
    public static final String DATA_TOPPLAYERS_GAMEPOINTS = "gamePoints";
    public static final String DATA_TOPPLAYERS_GAMEMODE = "gameMode";
    public static final String DATA_TOPPLAYERS_PLAYERNAME = "playerName";

    //GAMEPOOL TABLE CREATION
    public static final String TABLE_GAMEPOOL = "CREATE TABLE IF NOT EXISTS "+ DATA_GAMEPOOL_NAME +
            "( "+ DATA_GAMEPOOL_IMAGERES +" INTEGER PRIMARY KEY," +
            DATA_GAMEPOOL_HINT +" TEXT," +
            DATA_GAMEPOOL_ANSWER +" TEXT," +
            DATA_GAMEPOOL_GAMEMODE+ " INTEGER);";

    //TOPPLAYER TABLE CREATION
    public static final String TABLE_TOPPLAYERS = "CREATE TABLE IF NOT EXISTS "+ DATA_TOPPLAYERS_NAME +
                                                    "( "+ DATA_TOPPLAYERS_PLAYERNAME+" TEXT," +
                                                    DATA_TOPPLAYERS_GAMEPOINTS+" INTEGER," +
                                                    DATA_TOPPLAYERS_GAMEMODE+" INTEGER);";

    //GAMEPOOL TABLE QUERY
    public static final String[] QUERY_GAMEPOOL = {DATA_GAMEPOOL_IMAGERES,DATA_GAMEPOOL_HINT,
                                                    DATA_GAMEPOOL_ANSWER,DATA_GAMEPOOL_GAMEMODE};

    //TOPPLAYER TABLE QUERY
    public static final String[] QUERY_TOPPLAYERS = {DATA_TOPPLAYERS_PLAYERNAME,DATA_TOPPLAYERS_GAMEPOINTS,
                                                    DATA_TOPPLAYERS_GAMEMODE};

    //GAME OPTIONS TABLE
    public static final String DATA_BASEGAME_OPTIONSPREFERENCE = "optionsPreference";

    //SQLite database connector variable
    private SQLiteDatabase sqldb;

    //SQLite helper variable
    private SQLHelper dbHelper;

    /*
     * Constructor for this class
     * @param context, type Context : Activity that created this instance
     * @param version, type int : version of database to be created/opened.
     */
    public DatabaseConnector(Context context, int version) {
        dbHelper = new SQLHelper(context,DATABASE_NAME,version);
        try {
            sqldb = dbHelper.getWritableDatabase();
        } catch(SQLiteException ex) {
            sqldb = dbHelper.getReadableDatabase();
        }
    }

    /*
     * inserts data to GamePool table
     * @param gl, type GameLevel : model for insertion
     * @return bool
     */
    public boolean addGamePool(GamePool gp) {
        ContentValues cv = new ContentValues();
        try {

            cv.put(DATA_GAMEPOOL_IMAGERES, gp.getImageRes());
            cv.put(DATA_GAMEPOOL_ANSWER, gp.getAnswer());
            cv.put(DATA_GAMEPOOL_HINT, gp.getHint());
            cv.put(DATA_GAMEPOOL_GAMEMODE, gp.getGameMode());

            sqldb.insert(DATA_GAMEPOOL_NAME, null, cv);
            return true;

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * retrieves specific row from GamePool table
     * @param answer, type String : answer of GamePool to be retrieved. (answer is unique in database/no 2 or more questions have the same answer)
     * @return GamePool
     */
    public GamePool getGamePool(String answer) {
        Cursor cursor = sqldb.query(DATA_GAMEPOOL_NAME,QUERY_GAMEPOOL, DATA_GAMEPOOL_ANSWER + " =?",new String[] {answer},null,null,null);
        if(cursor.moveToFirst()) {
            GamePool gl = new GamePool();
            gl.setImageRes(cursor.getInt(0));
            gl.setHint(cursor.getString(1));
            gl.setAnswer(cursor.getString(2));
            gl.setGameMode(cursor.getInt(3));
            Log.e("TEST!!!!!!!!!!!!!!!!", String.valueOf(cursor.getInt(0)));
            return gl;
        }
        return null;
    }

    /*
     * retrieves data from GamePool table with respect to game mode.
     * @param gameMode, type int : gameMode to be retrieved from GamePool table.
     * @return List of GamePool
     */
    public List<GamePool> getGamePool(int gameMode) {
        List<GamePool> gamePoolList = new ArrayList<GamePool>();
        Cursor cursor = sqldb.query(DATA_GAMEPOOL_NAME, QUERY_GAMEPOOL, DATA_GAMEPOOL_GAMEMODE + " =?", new String[]{String.valueOf(gameMode)}, null, null, null);
        GamePool gl;
        while (cursor.moveToNext()) {
            gl = new GamePool();
            gl.setImageRes(cursor.getInt(0));
            gl.setHint(cursor.getString(1));
            gl.setAnswer(cursor.getString(2));
            gl.setGameMode(cursor.getInt(3));
            gamePoolList.add(gl);
        }
        return gamePoolList;
    }

    /*
     * inserts data to TopPlayer table
     * @param tp, type TopPlayer : model for insertion
     * @return bool
     */
    public boolean addTopPlayer(TopPlayer tp) {
        ContentValues cv = new ContentValues();
        try {
            cv.put(DATA_TOPPLAYERS_PLAYERNAME, tp.getPlayerName());
            cv.put(DATA_TOPPLAYERS_GAMEPOINTS, tp.getGamePoints());
            cv.put(DATA_TOPPLAYERS_GAMEMODE, tp.getGameMode());

            sqldb.insert(DATA_TOPPLAYERS_NAME, null, cv);
            return true;
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * retrieves data from TopPlayer table with respect to game mode.
     * @param gameMode, type int : gameMode to be retrieved from TopPlayer table.
     * @return Array of TopPlayer size:10
     */
    public TopPlayer[] getTopPlayer(int gameMode) {
        TopPlayer[] topPlayerArray = new TopPlayer[10];
        Cursor cursor = sqldb.query(DATA_TOPPLAYERS_NAME, QUERY_TOPPLAYERS, DATA_TOPPLAYERS_GAMEMODE + " =?", new String[] {String.valueOf(gameMode)}, null, null, DATA_TOPPLAYERS_GAMEPOINTS, String.valueOf("10"));
        TopPlayer tp;
        int count = 0;
        while(cursor.moveToNext()) {
            tp = new TopPlayer();
            tp.setPlayerName(cursor.getString(0));
            tp.setGamePoints(cursor.getInt(1));
            tp.setGameMode(cursor.getInt(2));
            topPlayerArray[count++] = tp;
        }
        return topPlayerArray;
    }

    //SQL helper class
    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context context, String DBNAME,int version) {
            super(context, DBNAME, null, version);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        @Override
         public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_GAMEPOOL);
            db.execSQL(TABLE_TOPPLAYERS);
        }

        @Override
        public String getDatabaseName() {
            return super.getDatabaseName();
        }
    }
}
