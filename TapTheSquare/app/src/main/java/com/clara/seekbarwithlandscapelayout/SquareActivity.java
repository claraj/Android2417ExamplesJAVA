package com.clara.seekbarwithlandscapelayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class SquareActivity extends AppCompatActivity {

    ImageView mSquare;

    public static final String EXTRA_TAP_INSIDE_SQUARE = "com.clara.seekbar.tap_inside_square";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);

        int squareSize = getIntent().getIntExtra(MainActivity.EXTRA_SQUARE_SIZE, 100);
        if (squareSize == 0) {
            squareSize = 1;
        }

        mSquare = findViewById(R.id.square_shape);

        LayoutParams params = mSquare.getLayoutParams();
        params.height = squareSize;
        params.width = squareSize;

        // If the user taps the square, return to MainActivity
        // Tell MainActivity that the task completed OK
        // and that the user did tap inside the square
        mSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent carries data back to Activity that launched this one
                Intent returnIntent = new Intent();
                // Add the value true as an extra
                returnIntent.putExtra(EXTRA_TAP_INSIDE_SQUARE, true);
                // Everything went well
                setResult(RESULT_OK, returnIntent);
                // End this Activity. Android will remove it from the screen,
                // then create and show the previous Activity - MainActivity in this case
                finish();
            }
        });

        // If the user misses the square, they will tap the background
        // Add a listener to the main content view, in effect, everything but the background.
        // Return to MainActivity
        // Tell MainActivity that the task completed OK   (still OK - even though they missed)
        // and that the user did NOT tap inside the square
        View mainView = findViewById(android.R.id.content);
        mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_TAP_INSIDE_SQUARE, false);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}


