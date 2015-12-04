package com.example.jownjie.nihingo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jownjie.nihingo.Models.GameLevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by User on 12/2/2015.
 */
public class DatabaseConnector {

    public static final String DATA_TABLE_GAMELEVEL_NAME = "GameLevel";
    public static final String DATA_TABLE_GAMELEVEL_LEVEL = "level";
    public static final String DATA_TABLE_GAMELEVEL_IMAGEDR = "imageDr";
    public static final String DATA_TABLE_GAMELEVEL_ANSWERED = "answered";
    public static final String DATA_TABLE_GAMELEVEL_ANSWER = "answer";


    public static final String TABLE_GAMELEVEL = "CREATE TABLE IF NOT EXISTS "+ DATA_TABLE_GAMELEVEL_NAME +
                                                    "("+DATA_TABLE_GAMELEVEL_LEVEL+" INTEGER PRIMARY KEY," +
                                                    DATA_TABLE_GAMELEVEL_IMAGEDR +" BLOB," +
                                                    DATA_TABLE_GAMELEVEL_ANSWERED +" INTEGER," +
                                                    DATA_TABLE_GAMELEVEL_ANSWER +" TEXT);";
    public static final String[] QUERY_TABLE_GAMELEVEL = {DATA_TABLE_GAMELEVEL_LEVEL,DATA_TABLE_GAMELEVEL_IMAGEDR,
                                                        DATA_TABLE_GAMELEVEL_ANSWERED,DATA_TABLE_GAMELEVEL_ANSWER};
    private SQLiteDatabase sqldb;
    private SQLHelper dbHelper;

    public DatabaseConnector(Context context, int version) {
        dbHelper = new SQLHelper(context,DATA_TABLE_GAMELEVEL_NAME,version);
        try {
            sqldb = dbHelper.getWritableDatabase();
        } catch(SQLiteException ex) {
            sqldb = dbHelper.getReadableDatabase();
        }
    }

    public boolean insertGameLevel(GameLevel gl, File file) {
        ContentValues cv = new ContentValues();
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] imageDr = new byte[fis.available()];
            fis.read(imageDr);

            cv.put(DATA_TABLE_GAMELEVEL_IMAGEDR, imageDr);
            cv.put(DATA_TABLE_GAMELEVEL_LEVEL, gl.getLevel());
            cv.put(DATA_TABLE_GAMELEVEL_ANSWERED, gl.isAnswered());
            cv.put(DATA_TABLE_GAMELEVEL_ANSWER, gl.getAnswer());

            sqldb.insert(DATA_TABLE_GAMELEVEL_NAME, null, cv);
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

    public GameLevel getGameLevel(Context context, String level) {
        Cursor cursor = sqldb.query(DATA_TABLE_GAMELEVEL_NAME,QUERY_TABLE_GAMELEVEL, DATA_TABLE_GAMELEVEL_LEVEL + " =?",new String[] {level},null,null,null);
        if(cursor.moveToFirst()) {
            GameLevel gl = new GameLevel();
            gl.setLevel(cursor.getInt(0));
            gl.setImageDr(cursor.getBlob(1));
            gl.setAnswered(cursor.getInt(2));
            gl.setAnswer(cursor.getString(3));
            return gl;
        }
        return null;
    }

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
