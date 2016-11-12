package com.example.android.flashcard.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.android.flashcard.FlashcardHolder;

import java.util.UUID;

/**
 * Created by Rohan Kanchana on 5/23/2016.
 */
public class AppCursorWrapper extends CursorWrapper {
    public AppCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public FlashcardHolder getFlashcard() {
        String uuidString = getString(getColumnIndex(DbSchema.FlashcardTable.Cols.UUID));
        String title = getString(getColumnIndex(DbSchema.FlashcardTable.Cols.QUESTION));
        String contents = getString(getColumnIndex(DbSchema.FlashcardTable.Cols.ANSWER));
        String collection = getString(getColumnIndex(DbSchema.FlashcardTable.Cols.COLLECTION));

        return new FlashcardHolder(UUID.fromString(uuidString), title, contents, collection);
    }
}
