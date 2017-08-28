package com.clara.seekbarlandscapelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarActivity extends AppCompatActivity {

	SeekBar mSeekBar;
	TextView mSeekBarLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seek_bar);


		mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
		mSeekBarLabel = (TextView) findViewById(R.id.seek_bar_value_label);

		// Set initial TextView value using a format string resource
		mSeekBarLabel.setText(getString(R.string.seekbar_progress, mSeekBar.getProgress()));

		addSeekBarListener();

	}

	private void addSeekBarListener() {

		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSeekBarLabel.setText(getString(R.string.seekbar_progress, progress));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// We don't care about this event in this app, but are required to implement this method.
				// It's ok that nothing happens here, our app doesn't need to do anything when this event happens.
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Same as previous method, can leave this empty.
			}
		});

	}


}



