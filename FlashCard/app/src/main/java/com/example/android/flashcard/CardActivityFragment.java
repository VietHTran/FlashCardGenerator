package com.example.android.flashcard;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class CardActivityFragment extends Fragment {

    public CardActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_card, container, false);
        final TextView question=(TextView) rootView.findViewById(R.id.input_question);
        final TextView answer=(TextView) rootView.findViewById(R.id.input_answer);
        Button save =(Button) rootView.findViewById(R.id.card_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q=question.getText().toString(),ans=answer.getText().toString();
                if (q.equals("") || ans.equals("")) {
                    Toast.makeText(getActivity(),"Please fill in both question and answer input", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(),"Card saved successfully", Toast.LENGTH_SHORT).show();
                lab.addFlashcard(holder);
                getActivity().finish();
            }
        });
        return rootView;
    }
}
