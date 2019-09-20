package com.clara.redbluefragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements RedFragment.OnRandomNumberGeneratedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the red Fragment, show on screen
        RedFragment redFragment = RedFragment.newInstance();
        redFragment.setOnRandomNumberGeneratedListener(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, redFragment);
        ft.commit();

    }

    @Override
    public void onRandomNumber(int number) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        BlueFragment blueFragment = BlueFragment.newInstance(number);
        ft.replace(R.id.fragment_container, blueFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
