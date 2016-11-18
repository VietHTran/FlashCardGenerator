package com.example.android.flashcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.flashcard.database.AppCursorWrapper;
import com.example.android.flashcard.database.DbSchema;
import com.example.android.flashcard.database.DbSchema.FlashcardTable;
import com.example.android.flashcard.database.FlashcardsBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Viet on 11/12/2016.
 */
public class FlashcardLab {
    private static FlashcardLab sFlashcardLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static FlashcardLab get(Context context) {
        if(sFlashcardLab == null) {
            sFlashcardLab = new FlashcardLab(context);
        }
        return sFlashcardLab;
    }

    private FlashcardLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new FlashcardsBaseHelper(mContext).getWritableDatabase();
    }

    public void addFlashcard(FlashcardHolder info) {
        ContentValues values = getContentValues(info);

        mDatabase.insert(FlashcardTable.NAME, null, values);
    }

    public void deleteFlashcard(UUID uuid) {
        mDatabase.delete(FlashcardTable.NAME, FlashcardTable.Cols.UUID+" = ?", new String[]{uuid.toString()});
    }

    public List<FlashcardHolder> getFlashcards() {
        List<FlashcardHolder> cardInformationList = new ArrayList<>();

        AppCursorWrapper cursor = queryCardInfos(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cardInformationList.add(cursor.getFlashcard());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return cardInformationList;
    }

    public FlashcardHolder getSpecificCardInfo(UUID id) {
        AppCursorWrapper cursor = queryCardInfos(
                FlashcardTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getFlashcard();
        } finally {
            cursor.close();
        }
    }

    public void updateCardInfo(FlashcardHolder info) {
        String uuidString = info.uuid.toString();
        ContentValues values = getContentValues(info);

        mDatabase.update(FlashcardTable.NAME, values, FlashcardTable.Cols.UUID + " = ?", new String[] {uuidString});
    }

    private static ContentValues getContentValues(FlashcardHolder cardInformation) {
        ContentValues values = new ContentValues();
        values.put(FlashcardTable.Cols.UUID, cardInformation.uuid.toString());
        values.put(FlashcardTable.Cols.QUESTION, cardInformation.question);
        values.put(FlashcardTable.Cols.ANSWER, cardInformation.answer);
        values.put(FlashcardTable.Cols.COLLECTION, cardInformation.collection);

        return values;
    }

    private AppCursorWrapper queryCardInfos(String whereClause,String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                FlashcardTable.NAME,
                null, // Columns = null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null  //orderBy
        );

        return new AppCursorWrapper(cursor);
    }
}
