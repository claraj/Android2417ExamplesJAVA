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
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

    }

    private void setProgressMessage() {
        int seekbarProgress = mSeekBar.getProgress();
        String progressMessage = getString(R.string.seekbar_progress_message, seekbarProgress);
        mSeekBarValueLabel.setText(progressMessage);
    }
}


