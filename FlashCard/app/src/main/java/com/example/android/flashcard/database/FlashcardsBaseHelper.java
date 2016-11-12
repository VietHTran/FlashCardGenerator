package com.example.android.flashcard.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rohan Kanchana on 11/6/2016.
 */
public class FlashcardsBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "flashcardValues.db";

    public FlashcardsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DbSchema.FlashcardTable.NAME +
                "(" + " _id integer primary key autoincrement, " +
                DbSchema.FlashcardTable.Cols.UUID + " INTEGER, " +
                DbSchema.FlashcardTable.Cols.QUESTION + " TEXT, " +
                DbSchema.FlashcardTable.Cols.ANSWER + " TEXT, " +
                DbSchema.FlashcardTable.Cols.COLLECTION + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
