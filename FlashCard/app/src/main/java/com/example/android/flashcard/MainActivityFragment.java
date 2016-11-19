package com.example.android.flashcard;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{
    Button collectionButton;
    Button reviewButton;
    Button voiceButton;
    static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
    public MainActivityFragment() {
    }
    @Override
    public void onResume() {
        super.onResume();
        if (DataManager.isChange) {
            DataManager.isChange=false;
            if (DataManager.message.equals("")) {
                Utility.showToastMessage(getActivity(),"Error: Unable to recognize voice");
            } else {
                Utility.handlingMessage(getActivity(),DataManager.message);
            }
            DataManager.message="";
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        collectionButton = (Button) view.findViewById(R.id.collection_main);
        collectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),CollectionsActivity.class);
                startActivityForResult((intent),69);
            }
        });

        reviewButton = (Button) view.findViewById(R.id.review_main);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ReviewCollectionActivity.class);
                startActivity(intent);
            }
        });
        voiceButton=(Button) view.findViewById(R.id.voice_command_main);
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(v);
                //Toast.makeText(getActivity(),"The option is under maintenance", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public void checkVoiceRecognition() {
        // Check if voice recognition is present
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            voiceButton.setEnabled(false);
            Utility.showToastMessage(getActivity(),"Voice recognition feature not present");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

            //If Voice recognition is successful then it returns RESULT_OK
            if(resultCode == Activity.RESULT_OK) {

                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty()) {
                    // If first Match contains the 'search' word
                    // Then start web search.
                    if (textMatchList.get(0).contains("search")) {

                        String searchQuery = textMatchList.get(0);
                        searchQuery = searchQuery.replace("search","");
                        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                        search.putExtra(SearchManager.QUERY, searchQuery);
                        startActivity(search);
                    } else {
                        Utility.showToastMessage(getActivity(),textMatchList.get(0));
                    }

                }
                //Result code for various error.
            }else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
                Utility.showToastMessage(getActivity(),"Audio Error");
            }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
                Utility.showToastMessage(getActivity(),"Client Error");
            }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
                Utility.showToastMessage(getActivity(),"Network Error");
            }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
                Utility.showToastMessage(getActivity(),"No Match");
            }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
                Utility.showToastMessage(getActivity(),"Server Error");
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
                .getPackage().getName());

        // Given an hint to the recognizer about what the user is going to say
        //There are two form of language model available
        //1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
        //2.LANGUAGE_MODEL_FREE_FORM  : If not sure about the words or phrases and its domain.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        // Specify how many results you want to receive. The results will be
        // sorted where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        //Start the Voice recognizer activity for the result.
        startActivityForResult(intent,VOICE_RECOGNITION_REQUEST_CODE);
    }
}
