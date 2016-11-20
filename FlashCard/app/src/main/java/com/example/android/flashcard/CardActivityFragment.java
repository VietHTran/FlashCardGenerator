package com.example.android.flashcard;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class CardActivityFragment extends Fragment {
    TextView question,answer;
    public CardActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_card, container, false);
        question=(EditText) rootView.findViewById(R.id.input_question);
        answer=(EditText) rootView.findViewById(R.id.input_answer);
        Button save =(Button) rootView.findViewById(R.id.card_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q=question.getText().toString(),ans=answer.getText().toString();
                if (q.equals("") || ans.equals("")) {
                    Utility.showToastMessage(getActivity(),"Please fill in both question and answer input");
                    return;
                }
                //Add to database
                FlashcardLab lab = FlashcardLab.get(getActivity());
                FlashcardHolder holder = new FlashcardHolder(q,
                        ans,
                        SingleCollectionActivity.mTitle);
                UUID uuid=holder.uuid;
                //Add flash cards to current data manager
                FlashCard flashCard= new FlashCard(q,ans,SingleCollectionActivity.mTitle,uuid);
                if (DataManager.collections.containsKey(SingleCollectionActivity.mTitle)) {
                    DataManager.collections.get(SingleCollectionActivity.mTitle).flashCards.add(flashCard);
                } else {
                    Collection collection= new Collection(SingleCollectionActivity.mTitle);
                    collection.flashCards.add(flashCard);
                    DataManager.collections.put(SingleCollectionActivity.mTitle,collection);
                    DataManager.names.add(collection);
                }
                Utility.showToastMessage(getActivity(),"Card saved successfully");
                lab.addFlashcard(holder);
                getActivity().finish();
            }
        });
        Button voiceInput=(Button)rootView.findViewById(R.id.card_voice);
        voiceInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(v);
            }
        });
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivityFragment.VOICE_RECOGNITION_REQUEST_CODE)

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
                        String res=textMatchList.get(0);
                        Log.v("test","thisisquestion "+question);
                        if (question.getText().toString().trim().equals("")){
                            question.setText(res);
                        } else {
                            answer.append(res);
                        }
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
        startActivityForResult(intent,MainActivityFragment.VOICE_RECOGNITION_REQUEST_CODE);
    }
}
