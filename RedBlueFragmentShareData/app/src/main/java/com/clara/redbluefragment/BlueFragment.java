package com.clara.redbluefragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass that shows a random number.
 */

public class BlueFragment extends Fragment {

    private final static String ARG_RANDOM = "number argument";
    private int mRandomNumber;

    public BlueFragment() {
        // Required empty public constructor
    }

    public static BlueFragment newInstance(int random) {
        BlueFragment fragment = new BlueFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RANDOM, random);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( getArguments() != null) {
            mRandomNumber = getArguments().getInt(ARG_RANDOM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blue, container, false);
        TextView randomTextView = view.findViewById(R.id.random_number_text_view);
        randomTextView.setText("Random: " + mRandomNumber);
        return view;
    }
}

