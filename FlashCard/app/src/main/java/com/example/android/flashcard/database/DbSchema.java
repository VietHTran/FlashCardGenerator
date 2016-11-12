package com.example.android.flashcard.database;

/**
 * Created by Rohan Kanchana on 5/21/2016.
 */
public class DbSchema {

    public static final class FlashcardTable {
        public static final String NAME = "flashcards_database";

        public static final class Cols {
            public static final String UUID = "flashcard_uuid";
            public static final String QUESTION = "flashcard_question";
            public static final String ANSWER = "flashcard_answer";
            public static final String COLLECTION = "flashcard_collection";
        }
    }
}
