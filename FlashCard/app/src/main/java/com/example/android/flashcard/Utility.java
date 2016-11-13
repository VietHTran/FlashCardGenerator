package com.example.android.flashcard;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Viet on 11/12/2016.
 */
public class Utility {
    public static void handlingMessage(Context context,String message) {
        Toast.makeText(context,"Error: Unable to recognize voice", Toast.LENGTH_SHORT).show();
    }
}
