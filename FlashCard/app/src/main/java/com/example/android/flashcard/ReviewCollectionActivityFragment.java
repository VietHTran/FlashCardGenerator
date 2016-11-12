package com.example.android.flashcard;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReviewCollectionActivityFragment extends Fragment {
    CollectionAdapter mCollectionAdapter;
    private ListView mListView;
    public ReviewCollectionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_review_collection, container, false);
        mCollectionAdapter=new CollectionAdapter(getActivity(),DataManager.names);
        mListView=(ListView)rootView.findViewById(R.id.review_collections_listView);
        mListView.setAdapter(mCollectionAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getActivity(),ReviewActivity.class);
                intent.putExtra("title",DataManager.names.get(position).name);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
