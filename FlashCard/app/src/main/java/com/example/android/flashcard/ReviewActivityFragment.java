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
            flashCards=DataManager.collections.get(name).flashCards;
            position=0;
        }
        if (position==-1) {
            getActivity().finish();
        }
        Button flip= (Button) root.findViewById(R.id.button_flip);
        Button next= (Button) root.findViewById(R.id.button_next);
        mTextView=(TextView) root.findViewById(R.id.textView_review);
        mTextView.setText(flashCards.get(position).question);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(flashCards.get(position).answer);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashCards.remove(position);
                if (flashCards.size()==0) {
                    Toast.makeText(getActivity(),"Review finished", Toast.LENGTH_SHORT).show();
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
