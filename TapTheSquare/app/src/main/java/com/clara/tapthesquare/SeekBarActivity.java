package com.clara.tapthesquare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SeekBarActivity extends AppCompatActivity {

	public static final String EXTRA_SQUARE_SIZE = "com.clara.tapthesquare.squaresize";

	private static final int SQUARE_REQUEST_CODE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seek_bar);

		final TextView seekbarValueTV = (TextView) findViewById(R.id.seek_bar_value_label);
		final SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);

		Button showSquare = (Button) findViewById(R.id.display_square_button);

		showSquare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent launchSquareActivity = new Intent(SeekBarActivity.this, SquareActivity.class);
				//Add data to the Intent using putExtra method
				launchSquareActivity.putExtra(EXTRA_SQUARE_SIZE, seekBar.getProgress());

				startActivityForResult(launchSquareActivity, SQUARE_REQUEST_CODE);
			}
		});


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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		//Need to check what request ended up with this result
		//And need to verify if the result was OK  - and not the user cancelling, or pressing back button

		if (requestCode == SQUARE_REQUEST_CODE && resultCode == RESULT_OK) {

			//Get the data from the Intent. Again, must provide a default value if the key is not found
			boolean tappedSquare = data.getBooleanExtra(SquareActivity.EXTRA_INSIDE_SQUARE, false);

			//Indicate result to user with a Toast
			if (tappedSquare) {
				Toast.makeText(this, "You tapped the square!", Toast.LENGTH_LONG).show();
			} else
				Toast.makeText(this, "Sorry, you missed the square", Toast.LENGTH_LONG).show();
		}

		if (requestCode == SQUARE_REQUEST_CODE && resultCode == RESULT_CANCELED) {
			//If the user presses the back button instead of completing the SquareActivity,
			//the result code is RESULT_CANCELLED. This means the SquareActivity would not have
			//had the chance to create an Intent to send data back to this Activity.
			Toast.makeText(this, "You pressed the back button", Toast.LENGTH_LONG).show();
		}
	}


	/*
	*
	// This method may be useful if you wanted to scale the max size of the square in relation to
	// the smaller dimension of the screen

 	private int getSmallestDimensionOfScreen() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager()
				.getDefaultDisplay()
				.getMetrics(dm);
		int height = dm.heightPixels;
		int width = dm.widthPixels;
		return Math.min(height, width);
	}

	* */

}