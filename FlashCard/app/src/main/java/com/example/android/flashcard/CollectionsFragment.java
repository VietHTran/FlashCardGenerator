package com.example.android.flashcard;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.content.Intent;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class CollectionsFragment extends Fragment {

    private static final String COLLECTIONS_DIALOG = "collections_dialog";
    private static final int REQUEST_COLLECTIONS = 0;
    private ListView mListView;
    CollectionAdapter mCollectionAdapter;
    public CollectionsFragment() {
    }
    //Setup the menu call by setHasOptionsMenu(true);
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.menu_collections_fragment,menu);
//        FlashcardLab lab = FlashcardLab.get(getActivity());
//        FlashcardHolder holder = new FlashcardHolder(question, answer, collection);
//        lab.addFlashcard(holder);
//        lab.deleteFlashcard(holder.uuid);
//        lab.getFlashcards();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        //Add new collection
        if (id==R.id.action_add) {
            FragmentManager manager = getActivity().getFragmentManager();
            CollectionsDialog dialog = CollectionsDialog.newInstance("Create collection:", "Name");
            dialog.setTargetFragment(CollectionsFragment.this, REQUEST_COLLECTIONS);
            dialog.show(manager, COLLECTIONS_DIALOG);
            return true;
        }
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.v("test","upupup");
        if (mListView!=null) {
            mCollectionAdapter=new CollectionAdapter(getActivity(),DataManager.names);
            mListView.setAdapter(mCollectionAdapter);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_COLLECTIONS) {
            String title = data.getStringExtra(CollectionsDialog.EXTRA_TITLE);
            if (DataManager.collections.containsKey(title)) {
                FragmentManager manager = getActivity().getFragmentManager();
                CollectionsDialog dialog = CollectionsDialog.newInstance("The collection you put in already exists:", "Name");
                dialog.setTargetFragment(CollectionsFragment.this, REQUEST_COLLECTIONS);
                dialog.show(manager, COLLECTIONS_DIALOG);
            } else {
                Intent intent= new Intent(getActivity(),SingleCollectionActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
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
        View rootView=inflater.inflate(R.layout.fragment_collections, container, false);
        mCollectionAdapter=new CollectionAdapter(getActivity(),DataManager.names);
        mListView=(ListView)rootView.findViewById(R.id.collection_list);
        mListView.setAdapter(mCollectionAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getActivity(),SingleCollectionActivity.class);
                intent.putExtra("title",DataManager.names.get(position).name);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
