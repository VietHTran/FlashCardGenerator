package com.example.android.flashcard;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.ISpeechDelegate;
import com.ibm.watson.developer_cloud.android.speech_to_text.v1.dto.SpeechConfiguration;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechModel;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{
    Button collectionButton;
    Button reviewButton;

    public MainActivityFragment() {
    }
    @Override
    public void onResume() {
        super.onResume();
        if (DataManager.isChange) {
            DataManager.isChange=false;
            if (DataManager.message.equals("")) {
                Toast.makeText(getActivity(),"Error: Unable to recognize voice", Toast.LENGTH_SHORT).show();
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
        Button voiceButton=(Button) view.findViewById(R.id.voice_command_main);
        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SoundAnalyzer.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
