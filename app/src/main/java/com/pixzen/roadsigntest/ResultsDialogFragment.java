package com.pixzen.roadsigntest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ResultsDialogFragment extends DialogFragment {

    public interface ResultsDialogListener {
        public void onResultDialogPositiveClick(DialogFragment dialog);
        public void onResultDialogNegativeClick(DialogFragment dialog);
    }

    ResultsDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ResultsDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ResultsDialogListener");
        }
    }


    public static ResultsDialogFragment newInstance(int totalNumberOfGuesses) {
        ResultsDialogFragment frag = new ResultsDialogFragment();
        Bundle args = new Bundle();
        args.putInt("totalNumberOfGuesses", totalNumberOfGuesses);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        int totalGuesses = getArguments().getInt("totalNumberOfGuesses");
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setMessage(
                getString(R.string.results,
                        totalGuesses,
                        (1000 / (double) totalGuesses)));

        // "Reset Quiz" Button
        builder.setPositiveButton(R.string.reset_quiz, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                listener.onResultDialogPositiveClick(ResultsDialogFragment.this);
            }
        });

        // "Quit" Button
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the negative button event back to the host activity
                listener.onResultDialogNegativeClick(ResultsDialogFragment.this);
            }
        });

        return builder.create(); // return the AlertDialog
    }
}