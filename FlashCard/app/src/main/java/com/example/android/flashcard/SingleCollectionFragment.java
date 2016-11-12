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
            Intent intent= new Intent(getActivity(),CardActivity.class);
            startActivity(intent);
            return true;
        } else if (id==R.id.action_delete) {
            //Delete all cards
            //Go back to Collection
            return true;
        }
        return true;
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
        }
        View rootView=inflater.inflate(R.layout.fragment_single_collection, container, false);
        mFlashCardAdapter=new FlashCardAdapter(getActivity(),DataManager.collections.get(SingleCollectionActivity.mTitle).flashCards);
        ListView listView=(ListView)rootView.findViewById(R.id.flash_cards_listview);
        listView.setAdapter(mFlashCardAdapter);
        return rootView;
    }
}
