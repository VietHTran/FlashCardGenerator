package com.example.android.flashcard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Viet on 11/12/2016.
 */
public class FlashCardAdapter extends ArrayAdapter<FlashCard> {
    public FlashCardAdapter(Activity context, List<FlashCard> flashCards) {
        super(context,0,flashCards);
    }
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        FlashCard i=getItem(pos);
        View root= LayoutInflater.from(getContext()).inflate(R.layout.list_item_flash_card,parent,false);
        TextView textView=(TextView) root;
        textView.setText(i.question);
        return root;
    }
}
