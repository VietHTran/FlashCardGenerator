package com.example.android.flashcard;

import java.util.UUID;

/**
 * Created by Rohan Kanchana on 11/12/2016.
 */
public class FlashcardHolder {
    public UUID uuid;
    public String question;
    public String answer;
    public String collection;

    public FlashcardHolder(String question, String answer, String collection) {
        this.question = question;
        this.answer = answer;
        this.collection = collection;
        uuid = UUID.randomUUID();
    }

    public FlashcardHolder(UUID uuid, String question, String answer, String collection) {
        this.uuid = uuid;
        this.question = question;
        this.answer = answer;
        this.collection = collection;
    }
}
