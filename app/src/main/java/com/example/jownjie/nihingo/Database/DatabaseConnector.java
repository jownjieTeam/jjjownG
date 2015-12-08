package com.example.jownjie.nihingo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jownjie.nihingo.Models.GamePool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by User on 12/2/2015.
 */
public class DatabaseConnector {

    //TEST TABLE
    public static final String DATA_GAMELEVEL_NAME = "TESTTABLE";
    public static final String DATA_GAMELEVEL_IMAGEDR = "imageDr";
    public static final String DATA_GAMELEVEL_ANSWER = "answer";
    public static final String DATA_GAMELEVEL_HINT = "hint";

    //TEST TABLE CREATION
    public static final String TABLE_GAMELEVEL = "CREATE TABLE IF NOT EXISTS "+ DATA_GAMELEVEL_NAME +
            "( "+ DATA_GAMELEVEL_IMAGEDR +" BLOB," +
            DATA_GAMELEVEL_HINT +" TEXT," +
            DATA_GAMELEVEL_ANSWER +" TEXT);";

    //TEST TABLE QUERY
    public static final String[] QUERY_TABLE_GAMELEVEL = {DATA_GAMELEVEL_IMAGEDR,
            DATA_GAMELEVEL_HINT,DATA_GAMELEVEL_ANSWER};






    //RANKINGS TABLE
    public static final String DATA_TOPPLAYERS_NAME = "TopPlayers";
    public static final String DATA_TOPPLAYERS_GAMEPOINTS = "gamePoints";
    public static final String DATA_TOPPLAYERS_GAMEMODE = "gameMode";
    public static final String DATA_TOPPLAYERS_PLAYERNAME = "playerName";

    //RANKINGS TABLE CREATION
    public static final String TABLE_TOPPLAYERS = "CREATE TABLE IF NOT EXISTS "+ DATA_TOPPLAYERS_NAME +
                                                    "( "+ DATA_TOPPLAYERS_PLAYERNAME+" TEXT," +
                                                    DATA_TOPPLAYERS_GAMEPOINTS+" INTEGER," +
                                                    DATA_TOPPLAYERS_GAMEMODE+" TEXT);";
    //RANKINGS TABLE QUERY
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
        dbHelper = new SQLHelper(context,DATA_GAMELEVEL_NAME,version);
        try {
            sqldb = dbHelper.getWritableDatabase();
        } catch(SQLiteException ex) {
            sqldb = dbHelper.getReadableDatabase();
        }
    }

    /*
     * Test method for database insertion at test table
     * @param gl, type GameLevel : model for insertion
     * @param file, type File : image to be inserted
     */
    public boolean insertGameLevel(GamePool gl, File file) {
        ContentValues cv = new ContentValues();
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] imageDr = new byte[fis.available()];
            fis.read(imageDr);

            cv.put(DATA_GAMELEVEL_IMAGEDR, imageDr);
            cv.put(DATA_GAMELEVEL_ANSWER, gl.getAnswer());
            cv.put(DATA_GAMELEVEL_HINT, gl.getHint());

            sqldb.insert(DATA_GAMELEVEL_NAME, null, cv);
            fis.close();
            return true;

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
     * Test method for retrieval of data in test table
     * @param level, type String : level of GameLevel to be retrieved. (level is unique in database)
     */
    public GamePool getGameLevel(String answer) {
        Cursor cursor = sqldb.query(DATA_GAMELEVEL_NAME,QUERY_TABLE_GAMELEVEL, DATA_GAMELEVEL_ANSWER + " =?",new String[] {answer},null,null,null);
        if(cursor.moveToFirst()) {
            GamePool gl = new GamePool();
            gl.setImageDr(cursor.getBlob(0));
            gl.setHint(cursor.getString(1));
            gl.setAnswer(cursor.getString(2));
            return gl;
        }
        return null;
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
            db.execSQL(TABLE_GAMELEVEL);
        }

        @Override
        public String getDatabaseName() {
            return super.getDatabaseName();
        }
    }
}
