package com.example.android.flashcard;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.flashcard.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CardCheckActivityFragment extends Fragment {
    int mPosition;
    public CardCheckActivityFragment() {
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.menu_card_check,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        //Add new collection
        if (id==R.id.action_delete) {
            FlashcardLab lab = FlashcardLab.get(getActivity());
            if (mPosition!=-1) {
                Collection collection=DataManager.collections.get(SingleCollectionActivity.mTitle);
                FlashCard card=collection.flashCards.remove(mPosition);
                lab.deleteFlashcard(card.id);
            }
            getActivity().finish();
            return true;
        }
        return true;
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
            mPosition=intent.getIntExtra("position",-1);
        }
        return rootView;
    }
}
