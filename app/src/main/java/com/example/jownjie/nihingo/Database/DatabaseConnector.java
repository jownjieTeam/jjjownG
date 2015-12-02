package com.example.jownjie.nihingo.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * Created by User on 12/2/2015.
 */
public class DatabaseConnector {
    SQLiteDatabase sqldb;
    SQLHelper dbHelper;

    public DatabaseConnector(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        dbHelper = new SQLHelper(context,dbName,factory,version);
        try {
            sqldb = dbHelper.getWritableDatabase();
        } catch(SQLiteException ex) {
            sqldb = dbHelper.getReadableDatabase();
        }
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public String getDatabaseName() {
            return super.getDatabaseName();
        }
    }
}
