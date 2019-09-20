package com.clara.redbluefragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class RedFragment extends Fragment {

    public interface OnRandomNumberGeneratedListener {
        void onRandomNumber(int number);
    }

    OnRandomNumberGeneratedListener listener;
    Random random;

    public RedFragment() {
        // Required empty public constructor
        random = new Random();
    }

    public static RedFragment newInstance() {
        return new RedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_red, container, false);
        Button randomButton = view.findViewById(R.id.random_number_button);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = random.nextInt(100);
                listener.onRandomNumber(number);
            }
        });

        return view;
    }

    public void setOnRandomNumberGeneratedListener(OnRandomNumberGeneratedListener listener) {
        this.listener = listener;
    }
}


