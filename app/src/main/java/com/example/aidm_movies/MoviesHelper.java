package com.example.aidm_movies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MoviesHelper extends SQLiteOpenHelper {

    // este incrementat de fiecare data cand se modifica DB
    private static final int DATABASE_VERSION = 1;

    // Numele fisierului local in dispozitivul mobil care salveaza toate datele
    private static final String DATABASE_NAME = "movies.db";

    //constructor care apeleaza constructorul superclasei folosind context
    //argumentul 3 reprezinta cursor factory si este null, nefiind folosit aici
    public MoviesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + MoviesContract.UsersEntry.TABLE_NAME + " (" +
                MoviesContract.UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.UsersEntry.COLUMN_USERNAME + " TEXT NOT NULL, " +
                MoviesContract.UsersEntry.COLUMN_PASSWORD + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_USERS_TABLE);
        Log.d("MOVIEHELPER", " creat users");

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " (" +
                MoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.MoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_YEAR + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + MoviesContract.MoviesEntry.COLUMN_USER_ID + ") REFERENCES " +
                MoviesContract.UsersEntry.TABLE_NAME + " (" + MoviesContract.UsersEntry._ID +
                " ));" ;
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
        Log.d("MOVIEHELPER", " creat movies");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.UsersEntry.TABLE_NAME);
        Log.d("onUpgrade DB", " ");

        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        Log.d("onUpgrade DB", " ");

        // Creeaza tabela din nou
        onCreate(db);
    }
}
