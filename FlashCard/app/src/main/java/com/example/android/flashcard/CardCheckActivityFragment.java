package com.example.android.flashcard;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.flashcard.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CardCheckActivityFragment extends Fragment {

    public CardCheckActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent=getActivity().getIntent();
        View rootView=inflater.inflate(R.layout.fragment_card_check, container, false);
        if (intent!=null) {
            TextView question=(TextView)rootView.findViewById(R.id.question_textView);
            TextView answer=(TextView)rootView.findViewById(R.id.answer_textView);
            question.setText(intent.getStringExtra("question"));
            answer.setText(intent.getStringExtra("answer"));
        }
        return rootView;
    }
}
