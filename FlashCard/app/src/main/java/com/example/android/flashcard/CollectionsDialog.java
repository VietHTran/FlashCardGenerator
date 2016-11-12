package com.example.android.flashcard;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Viet on 11/12/2016.
 */
public class CollectionsDialog extends DialogFragment {


    private static final String ARG_DIALOG_TITLE = "arg_dialog_title";
    private static final String ARG_DIALOG_HINT = "arg_dialog_hint";

    public static final String EXTRA_TITLE = "com.example.android.flashcard.title";

    public static CollectionsDialog newInstance(String dialogTitle, String dialogHint) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIALOG_TITLE, dialogTitle);
        args.putSerializable(ARG_DIALOG_HINT, dialogHint);

        CollectionsDialog fragment = new CollectionsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = (String) getArguments().getSerializable(ARG_DIALOG_TITLE);
        String hint = (String) getArguments().getSerializable(ARG_DIALOG_HINT);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.collections_dialog, null);

        final EditText editText = (EditText) v.findViewById(R.id.edittext);
        editText.setHint(hint);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(title)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResultWithExtra(Activity.RESULT_OK, editText.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_CANCELED);
                            }

                        })
                .create();
    }

    private void sendResult(int resultCode) {

        if (getTargetFragment() == null) {
            return;
        }

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, null);
    }

    private void sendResultWithExtra(int resultCode, String string) {

        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, string);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}

