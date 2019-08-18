package com.clara.tsalinerandomizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mTapHereButton;
    private ImageButton mRightArrow;
    private ImageButton mLeftArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTapHereButton = findViewById(R.id.tap_here_button);
        mRightArrow = findViewById(R.id.right_arrow);
        mLeftArrow = findViewById(R.id.left_arrow);

        mTapHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRandomArrow();
            }
        });

        mRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        mLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

    }


    /* Hide button, pick an arrow ImageButton at random and show that */
    private void showRandomArrow() {

        // Remove Button
        mTapHereButton.setVisibility(View.GONE);

        // Pick a random boolean
        boolean rightArrow = new Random().nextBoolean();

        if (rightArrow) {
            mRightArrow.setVisibility(View.VISIBLE);
        } else {
            mLeftArrow.setVisibility(View.VISIBLE);
        }

    }

    /* Reset to initial app state with button visible, arrows gone */
    private void reset() {

        // Hide arrow ImageButtons
        mRightArrow.setVisibility(View.GONE);
        mLeftArrow.setVisibility(View.GONE);

        // Show Tap Here button
        mTapHereButton.setVisibility(View.VISIBLE);

    }


}
