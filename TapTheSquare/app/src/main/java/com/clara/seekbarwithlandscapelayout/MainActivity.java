package com.clara.seekbarwithlandscapelayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar mSeekBar;
    TextView mSeekBarValueLabel;
    Button mShowSquareButton;

    public static final String EXTRA_SQUARE_SIZE = "com.clara.seekbar.square_size";

    private static final int SQUARE_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar = findViewById(R.id.seekBar);
        mSeekBarValueLabel = findViewById(R.id.seekbarValueLabel);
        mShowSquareButton = findViewById(R.id.show_square_button);

        setProgressMessage();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setProgressMessage();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Don't need to respond to this event so can leave this empty
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Don't need to respond to this event so can leave this empty
            }
        });

        mShowSquareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSquareIntent = new Intent(MainActivity.this, SquareActivity.class);
                showSquareIntent.putExtra(EXTRA_SQUARE_SIZE, mSeekBar.getProgress());
                startActivityForResult(showSquareIntent, SQUARE_REQUEST_CODE);
            }
        });

    }

    private void setProgressMessage() {
        int seekbarProgress = mSeekBar.getProgress();
        // seekbar_progress_message is a format string, need to provide the int value
        // as the second argument to getString, to fill in the %1$d placeholder
        String progressMessage = getString(R.string.seekbar_progress_message, seekbarProgress);
        mSeekBarValueLabel.setText(progressMessage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SQUARE_REQUEST_CODE && resultCode == RESULT_OK) {
            boolean didTapSquare = data.getBooleanExtra(SquareActivity.EXTRA_TAP_INSIDE_SQUARE, false);

            if (didTapSquare) {
                Toast.makeText(this, "You tapped the square!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Sorry, you missed the square", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == SQUARE_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "You pressed the back button", Toast.LENGTH_LONG).show();
        }
    }
}


