package com.example.android.flashcard;

import java.util.ArrayList;

/**
 * Created by Viet on 11/12/2016.
 */
public class Collection {
    public String name;
    public ArrayList<FlashCard> flashCards;
    public Collection(String name) {
        this.name=name;
    }
}
