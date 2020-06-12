package com.example.myenglish;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WordsDb";
    static final String TABLE_WORDS = "Words";

    static final String KEY_ID = "_id";
    static final String KEY_WORDS = "English";
    static final String KEY_TRANSLATE = "Russian";
    static final String KEY_BOOLEAN = "bool";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_WORDS + "(" + KEY_ID
                + " integer primary key," + KEY_WORDS + " text," + KEY_TRANSLATE + " text," + KEY_BOOLEAN + " integer" +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_WORDS);
        onCreate(db);
    }
}