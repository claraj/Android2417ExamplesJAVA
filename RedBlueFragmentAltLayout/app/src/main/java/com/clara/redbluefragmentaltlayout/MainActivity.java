package com.clara.redbluefragmentaltlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements RedFragment.OnRandomNumberGeneratedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the red Fragment, show on screen
        RedFragment redFragment = RedFragment.newInstance();
        redFragment.setOnRandomNumberGeneratedListener(this);

        BlueFragment blueFragment = BlueFragment.newInstance(0);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.red_fragment_container, redFragment);
        ft.add(R.id.blue_fragment_container, blueFragment);

        ft.commit();


    }

    @Override
    public void onRandomNumber(int number) {
        //Create and show new BlueFragment, providing the number in a bundle

        FragmentManager fm = getSupportFragmentManager();
        BlueFragment blueFragment = (BlueFragment) fm.findFragmentById(R.id.blue_fragment_container);
        blueFragment.setRandomNumber(number);

    }
}
