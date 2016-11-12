package com.example.android.flashcard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Viet on 11/12/2016.
 */
public class CollectionAdapter extends ArrayAdapter<Collection> {
    public CollectionAdapter(Activity context, List<Collection> posters) {
        super(context,0,posters);
    }
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        Collection i=getItem(pos);
        View root= LayoutInflater.from(getContext()).inflate(R.layout.list_item_collection,parent,false);
        TextView textView=(TextView) root;
        textView.setText(i.name);
        return root;
    }
}
