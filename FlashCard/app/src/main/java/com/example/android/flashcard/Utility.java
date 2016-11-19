package com.example.android.flashcard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Viet on 11/12/2016.
 */
public class Utility {
    static String original;
    public static void handlingMessage(Context context,String message) {
        message=message.toLowerCase();
        String[] words=message.split(" ");
        showToastMessage(context,message);
        if (words.length==0) {
            Toast.makeText(context,"Error: Unable to recognize voice", Toast.LENGTH_SHORT).show();
        }
        if (words[0].equals("review")){
            String sub="";
            if (message.length()>7) {
                sub=message.substring(7);
            }
            Intent intent;
            if (!sub.equals("")&&checkCollectionName(sub,context)) {
                intent= new Intent(context,ReviewActivity.class);
                intent.putExtra("title",original);
            } else {
                intent= new Intent(context,ReviewCollectionActivity.class);
            }
            context.startActivity(intent);
        } else if (words[0].equals("collections") || words[0].equals("collection")
                || words[0].equals("connection") || words[0].equals("connections")) {
            Intent intent= new Intent(context,CollectionsActivity.class);
            context.startActivity(intent);
        } else if (checkCollectionName(message,context)){
            Intent intent= new Intent(context,SingleCollectionActivity.class);
            intent.putExtra("title",original);
            context.startActivity(intent);
        } else {
            showToastMessage(context,"Error: Unable to understand command \""+message+"\"");
        }
    }
    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static boolean checkCollectionName(String collection,Context context) {
        for (int i=0;i<DataManager.names.size();i++) {
            String name=DataManager.names.get(i).name.toLowerCase();
            if (collection.equals(name)) {
                original=DataManager.names.get(i).name;
                return true;
            }
        }
        return false;
    }

}
