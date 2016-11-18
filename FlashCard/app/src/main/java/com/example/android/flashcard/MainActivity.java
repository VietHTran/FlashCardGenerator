package com.example.android.flashcard;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
    }
    private void initData(){
        DataManager dataManager= new DataManager();
        FlashcardLab lab = FlashcardLab.get(this);
        List<FlashcardHolder> list=lab.getFlashcards();
        for (int i=0;i<list.size();i++) {
            FlashcardHolder holder= list.get(i);
            FlashCard flashCard=new FlashCard(holder.question,holder.answer,holder.collection,holder.uuid);
            if (!DataManager.collections.containsKey(holder.collection)) {
                Collection collection= new Collection(holder.collection);
                collection.flashCards.add(flashCard);
                DataManager.collections.put(holder.collection,collection);
                DataManager.names.add(collection);
            } else {
                DataManager.collections.get(holder.collection).flashCards.add(flashCard);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
