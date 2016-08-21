package com.clara.seekbarlandscapelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seek_bar);

		final TextView seekbarValueTV = (TextView) findViewById(R.id.seek_bar_value_label);
		SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);

		int seekbarProgress = seekBar.getProgress();

		seekbarValueTV.setText("The intital SeekBar value is " + seekbarProgress);

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				//The onProgessChanged method supplies the current SeekBar progress
				seekbarValueTV.setText("The seekbar value is " + progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				//Don't need to respond to this event, but still need to implement this method
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				//same here
			}
		});
	}
}