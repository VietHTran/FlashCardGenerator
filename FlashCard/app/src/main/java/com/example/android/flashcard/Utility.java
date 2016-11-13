package com.example.android.flashcard;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Viet on 11/12/2016.
 */
public class Utility {
    public static void handlingMessage(Context context,String message) {
        String[] words=message.split(" ");
        if (words.length==0) {
            Toast.makeText(context,"Error: Unable to recognize voice", Toast.LENGTH_SHORT).show();
        }
        for (int i=0;i<words.length;i++) {
            words[i].toLowerCase();
        }
        if (words[0].equals("review")){
            Intent intent= new Intent(context,ReviewActivity.class);
            context.startActivity(intent);
        } else if (words[0].equals("collections")) {
            Intent intent= new Intent(context,CollectionsActivity.class);
            context.startActivity(intent);
        } else if (DataManager.collections.containsKey(words[0])) {
            Intent intent= new Intent(context,SingleCollectionActivity.class);
            intent.putExtra("title",words[0]);
            context.startActivity(intent);
        } else {
            Toast.makeText(context,"Error: Unable to understand command", Toast.LENGTH_SHORT).show();
        }
    }
}
