package com.example.android.flashcard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Viet on 11/12/2016.
 */
public class DataManager {
    public static HashMap<String,Collection> collections;
    public static ArrayList<Collection> names;
    public static String message;
    public static boolean isChange;
    public DataManager() {
        collections=new HashMap<String, Collection>();
        names= new ArrayList<Collection>();
        message="";
        isChange=false;
    }
}
