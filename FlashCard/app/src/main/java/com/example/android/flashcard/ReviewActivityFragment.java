package com.example.android.flashcard;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReviewActivityFragment extends Fragment {
    private int position;
    private ArrayList<FlashCard> flashCards;
    private TextView mTextView;
    public ReviewActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_review, container, false);
        Intent intent=getActivity().getIntent();
        position=-1;
        if (intent!=null) {
            String name=intent.getStringExtra("title");
            flashCards=new ArrayList<FlashCard>(DataManager.collections.get(name).flashCards);
            position=0;
        }
        if (position==-1 || flashCards.size()==0) {
            getActivity().finish();
            Utility.showToastMessage(getActivity(),"empty collection");
            return root;
        }
        Button flip= (Button) root.findViewById(R.id.button_flip);
        Button next= (Button) root.findViewById(R.id.button_next);
        mTextView=(TextView) root.findViewById(R.id.textView_review);
        mTextView.setText(flashCards.get(position).question);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=flashCards.get(position).answer;
                if (mTextView.getText().equals(text)) {
                    mTextView.setText(flashCards.get(position).question);
                } else {
                    mTextView.setText(flashCards.get(position).answer);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashCards.remove(position);
                if (flashCards.size()==0) {
                    Utility.showToastMessage(getActivity(),"Review finished");
                    getActivity().finish();
                } else {
                    position=new Random().nextInt(flashCards.size());
                    mTextView.setText(flashCards.get(position).question);
                }
            }
        });
        return root;
    }
}
