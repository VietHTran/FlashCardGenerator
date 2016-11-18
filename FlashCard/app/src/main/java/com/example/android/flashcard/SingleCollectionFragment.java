package com.example.android.flashcard;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SingleCollectionFragment extends Fragment {
    private FlashCardAdapter mFlashCardAdapter;
    private ListView mListView;
    private static int counter=0;
    public SingleCollectionFragment() {
    }
    //Setup the menu call by setHasOptionsMenu(true);
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.menu_single_collection_fragment,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_add) {
            //Go to add CardActivity
            counter=1;
            Intent intent= new Intent(getActivity(),CardActivity.class);
            startActivity(intent);
            return true;
        } else if (id==R.id.action_delete) {
            //Delete all cards
            if (DataManager.collections.containsKey(SingleCollectionActivity.mTitle)) {
                ArrayList<FlashCard> cards=DataManager.collections.get(SingleCollectionActivity.mTitle).flashCards;
                //Go back to Collection
                for (int i=0;i<cards.size();i++) {
                    FlashcardLab lab = FlashcardLab.get(getActivity());
                    lab.deleteFlashcard(cards.get(i).id);
                }
            }
            DataManager.names.remove(DataManager.collections.get(SingleCollectionActivity.mTitle));
            DataManager.collections.remove(SingleCollectionActivity.mTitle);
            getActivity().finish();
            return true;
        }
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (DataManager.collections.containsKey(SingleCollectionActivity.mTitle) && mListView!=null) {
            updateList();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent=getActivity().getIntent();
        if (intent!=null) {
            SingleCollectionActivity.mTitle=intent.getStringExtra("title");
            getActivity().setTitle(SingleCollectionActivity.mTitle);
        }
        View rootView=inflater.inflate(R.layout.fragment_single_collection, container, false);
        mListView=(ListView)rootView.findViewById(R.id.flash_cards_listview);
        if (DataManager.collections.containsKey(SingleCollectionActivity.mTitle)) {
            updateList();
        }
        return rootView;
    }
    private void updateList() {
        mFlashCardAdapter=new FlashCardAdapter(getActivity(),DataManager.collections.get(SingleCollectionActivity.mTitle).flashCards);
        mListView.setAdapter(mFlashCardAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<FlashCard> flashCards=DataManager.collections.get(SingleCollectionActivity.mTitle).flashCards;
                Intent intent= new Intent(getActivity(),CardCheckActivity.class);
                intent.putExtra("question",flashCards.get(position).question);
                intent.putExtra("answer",flashCards.get(position).answer);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
}
