package com.clara.redbluefragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements RedFragment.OnRandomNumberGeneratedListener {

    private RedFragment redFragment;
    private BlueFragment blueFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the two Fragments
        redFragment = new RedFragment();
        blueFragment = new BlueFragment();

        // Show one of the fragments
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, redFragment);
        ft.commit();

    }

    @Override
    public void onRandomNumber(int number) {
        // TODO send random number to Blue

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle args = new Bundle();
        args.putInt(BlueFragment.ARG_RANDOM, number);
        blueFragment.setArguments(args);

        ft.replace(R.id.fragment_container, blueFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    }

    //        View mainView = findViewById(android.R.id.content);
//        mainView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                swapFragments();
//            }
//        });


//
//    private void swapFragments() {
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//
//        // If showing the RedFragment,
//        if (fm.findFragmentById(R.id.fragment_container) == redFragment) {
//            // Replace with blue fragment
//            ft.replace(R.id.fragment_container, blueFragment);
//        } else {
//            // Replace with red fragment
//            ft.replace(R.id.fragment_container, redFragment);
//        }
//
//        ft.commit();
//    }
//
//




