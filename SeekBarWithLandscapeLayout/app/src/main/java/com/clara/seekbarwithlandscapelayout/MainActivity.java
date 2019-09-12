package com.clara.seekbarwithlandscapelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar mSeekBar;
    TextView mSeekBarValueLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar = findViewById(R.id.seekBar);
        mSeekBarValueLabel = findViewById(R.id.seekbarValueLabel);

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

    }

    private void setProgressMessage() {
        int seekbarProgress = mSeekBar.getProgress();
        // seekbar_progress_message is a format string, need to provide the int value
        // as the second argument to getString, to fill in the %1$d placeholder
        String progressMessage = getString(R.string.seekbar_progress_message, seekbarProgress);
        mSeekBarValueLabel.setText(progressMessage);
    }
}


