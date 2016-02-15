package com.example.jownjie.nihingo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jownjie.nihingo.Models.GamePool;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;
import com.example.jownjie.nihingo.Models.TopPlayer;
import com.example.jownjie.nihingo.R;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/2/2015.
 */
public class DatabaseConnector {

    //first time setup variables
    private static int[] drawableIds;
    private static int[] rawIds;

    //DATABASE NAME
    private  final String DATABASE_NAME = "UNOFFICIAL_SPELLIT";

    //GAMEPOOL TABLE VARIABLES
    private  final String DATA_GAMEPOOL_NAME = "GamePool";
    private  final String DATA_GAMEPOOL_IMAGERES = "imageRes";
    private  final String DATA_GAMEPOOL_SOUNDRES = "soundRes";
    private  final String DATA_GAMEPOOL_ANSWER = "answer";
    private  final String DATA_GAMEPOOL_GAMEMODE = "gameMode";
    private  final String DATA_GAMEPOOL_LEVEL = "level";
    private  final String DATA_GAMEPOOL_CLASS = "class";

    //TOPPLAYER TABLE VARIABLES
    private  final String DATA_TOPPLAYERS_NAME = "TopPlayers";
    private  final String DATA_TOPPLAYERS_GAMEPOINTS = "gamePoints";
    private  final String DATA_TOPPLAYERS_PLAYERNAME = "playerName";

    //GAMEPOOL TABLE CREATION
    private  final String TABLE_GAMEPOOL = "CREATE TABLE IF NOT EXISTS "+ DATA_GAMEPOOL_NAME +
                                            "( "+ DATA_GAMEPOOL_IMAGERES +" INTEGER PRIMARY KEY," +
                                            DATA_GAMEPOOL_SOUNDRES +" INTEGER," +
                                            DATA_GAMEPOOL_ANSWER +" TEXT," +
                                            DATA_GAMEPOOL_GAMEMODE+ " INTEGER," +
                                            DATA_GAMEPOOL_LEVEL + " INTEGER," +
                                            DATA_GAMEPOOL_CLASS + " INTEGER);";

    //TOPPLAYER TABLE CREATION
    private  final String TABLE_TOPPLAYERS = "CREATE TABLE IF NOT EXISTS "+ DATA_TOPPLAYERS_NAME +
                                                    "( "+ DATA_TOPPLAYERS_PLAYERNAME+" TEXT," +
                                                    DATA_TOPPLAYERS_GAMEPOINTS+" INTEGER);";

    //GAMEPOOL TABLE QUERY
    private  final String[] QUERY_GAMEPOOL = {DATA_GAMEPOOL_IMAGERES,DATA_GAMEPOOL_SOUNDRES,DATA_GAMEPOOL_ANSWER,
                                                DATA_GAMEPOOL_GAMEMODE,DATA_GAMEPOOL_LEVEL,DATA_GAMEPOOL_CLASS};

    //TOPPLAYER TABLE QUERY
    private  final String[] QUERY_TOPPLAYERS = {DATA_TOPPLAYERS_PLAYERNAME,DATA_TOPPLAYERS_GAMEPOINTS};

    //GAME OPTIONS TABLE
    private  final String DATA_BASEGAME_OPTIONSPREFERENCE = "optionsPreference";

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
    private boolean addGamePool(GamePool gp,SQLiteDatabase sqldb) {
        ContentValues cv = new ContentValues();
        try {

            cv.put(DATA_GAMEPOOL_IMAGERES, gp.getImageRes());
            cv.put(DATA_GAMEPOOL_SOUNDRES, gp.getSoundRes());
            cv.put(DATA_GAMEPOOL_ANSWER, gp.getAnswer());
            cv.put(DATA_GAMEPOOL_GAMEMODE, gp.getGameMode());
            cv.put(DATA_GAMEPOOL_LEVEL, gp.getLevel());
            cv.put(DATA_GAMEPOOL_CLASS, gp.getClassification());
            Log.e("GAME POOL",gp.toString());
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
            gl.setSoundRes(cursor.getInt(1));
            gl.setAnswer(cursor.getString(2));
            gl.setGameMode(cursor.getInt(3));
            gl.setLevel(cursor.getInt(4));
            gl.setClassi(cursor.getInt(5));
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
            gl.setSoundRes(cursor.getInt(1));
            gl.setAnswer(cursor.getString(2));
            gl.setGameMode(cursor.getInt(3));
            gl.setLevel(cursor.getInt(4));
            gl.setClassi(cursor.getInt(5));
            gamePoolList.add(gl);
        }
        Log.e("RETRIEVE COUNT OF GAME","mode->"+gameMode+" count->"+gamePoolList.size());
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
    public TopPlayer[] getTopPlayer() {
        TopPlayer[] topPlayerArray = new TopPlayer[10];
        Cursor cursor = sqldb.query(DATA_TOPPLAYERS_NAME, QUERY_TOPPLAYERS, null, null, null, null, DATA_TOPPLAYERS_GAMEPOINTS + " DESC", "10");
        TopPlayer tp;
        int count = 0;
        while(cursor.moveToNext()) {
            tp = new TopPlayer();
            tp.setPlayerName(cursor.getString(0));
            tp.setGamePoints(cursor.getInt(1));
            topPlayerArray[count++] = tp;
            Log.e("PLAYER NAME", tp.getPlayerName()+"");
        }
        return topPlayerArray;
    }

    //SQL helper class
    public class SQLHelper extends SQLiteOpenHelper {
        Context context;
        public SQLHelper(Context context, String DBNAME,int version) {
            super(context, DBNAME, null, version);
            this.context = context;
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
            try {
                if(firstTimeSetup(context.getResources(), R.drawable.class, R.raw.class, db))
                    Log.e("FIRST TIME SETUP","SUCCESS!");
                else
                    Log.e("FIRST TIME SETUP","FAILED!");
            } catch(Exception e) {
                e.printStackTrace();
            }
            db.execSQL(TABLE_TOPPLAYERS);
        }

        @Override
        public String getDatabaseName() {
            return super.getDatabaseName();
        }
    }



    /*
     * The following code exists for the purpose of first time set up of the application.
     * @loadImageFiles, creates an array of image ids from the drawable resource directory.
     * @loadAudioFiles, creates an array of audio ids from the raw resource directory.
     * @insertGamePoolIntoDatabase, inserts all of the game pool objects into the GamePool table of the database.
     */
    public boolean firstTimeSetup(Resources resources,Class<?> drawablesResource, Class<?> rawResource, SQLiteDatabase sqldb) throws Exception {
        loadImageFiles(drawablesResource,resources);
        Log.e("SET UP PART 1", "OK!");
        //loadAudioFiles(rawResource);
        //Log.e("SET UP PART 2", "OK!");
        insertGamePoolIntoDatabase(resources,sqldb);
        Log.e("SET UP PART 2", "OK!");
        return true;
    }

    private void loadImageFiles(Class<?> drawablesResource,Resources resources){
        final Field[] fields = drawablesResource.getDeclaredFields();
        drawableIds = new int[fields.length];
        int i = 0;
        for (Field field : fields) {
            final int drawableId;
            try {
                drawableId = field.getInt(drawablesResource);
            } catch (Exception e) {
                continue;
            }
            if(validGameResource(drawableId, resources)) {
                drawableIds[i] = drawableId;
                i++;
            }
        }
    }

    /*private void loadAudioFiles(Class<?> rawResource) {
        final Field[] fields = rawResource.getDeclaredFields();
        rawIds = new int[fields.length];
        int i = 0;
        for (Field field : fields) {
            final int rawId;
            try {
                rawId = field.getInt(rawResource);
            } catch (Exception e) {
                continue;
            }
            if(rawId!=0) {
                rawIds[i] = rawId;
                i++;
            }
        }
    }*/

    private void insertGamePoolIntoDatabase(Resources resources,SQLiteDatabase sqldb) {
        int five=0, seven=0, nine=0, empty=0;
        List<GamePool> gamePoolList = new ArrayList<>();
        GamePool gp;
        int classification;
        if(resources!=null) {
            for (int i = 0; i < drawableIds.length; i++) {
                if (drawableIds[i] != 0) {
                    final String Imagetemp = resources.getResourceEntryName(drawableIds[i]);
                    gp = new GamePool();
                    gp.setImageRes(drawableIds[i]);
                    gp.setAnswerResource(Imagetemp);
                    gp.setGameMode(Imagetemp);
                    gp.setClassification(gp.getAnswer().length());
                    gamePoolList.add(gp);
                    classification = gp.getClassification();
                    if(classification== BaseGame.POOL_SHORT)
                        five++;
                    else if(classification==BaseGame.POOL_MEDIUM)
                        seven++;
                    else if(classification==BaseGame.POOL_LONG)
                        nine++;
                    else
                        empty++;
                }
            }
            /*
            for (int i = 0; i < rawIds.length; i++) {
                if (rawIds[i] != 0) {
                    for (GamePool gpo : gamePoolList) {
                        final String Audiotemp = resources.getResourceEntryName(rawIds[i]);
                        if (gpo.getAnswer().contentEquals(Audiotemp)) {
                            gpo.setSoundRes(rawIds[i]);
                            break;
                        }
                    }
                }
            }
            */

            for(GamePool gpo : gamePoolList) {
                addGamePool(gpo, sqldb);
                Log.e("insertGamePool", gpo.toString());
            }
            Log.e("COUNT OF FIVE",five+"");
            Log.e("COUNT OF SEVEN",seven+"");
            Log.e("COUNT OF NINE",nine+"");
            Log.e("COUNT OF NULL",empty+"");
        }
    }

    //helper method for filtering accepted images
    private static boolean validGameResource(int drawableId, Resources resources) {
        String temp = resources.getResourceEntryName(drawableId);
        return (temp.contains("generalinfo") || temp.contains("technology") || temp.contains("science")) && temp.split("_").length > 1;
    }
}
