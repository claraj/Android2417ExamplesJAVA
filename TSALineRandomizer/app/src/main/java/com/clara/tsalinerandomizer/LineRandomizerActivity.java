package com.clara.tsalinerandomizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Random;

public class LineRandomizerActivity extends AppCompatActivity {

	private Button mTapHereButton;
	private ImageButton mRightArrow;
	private ImageButton mLeftArrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_randomizer);

		mTapHereButton = (Button) findViewById(R.id.tap_here_button);
		mRightArrow = (ImageButton) findViewById(R.id.right_arrow);
		mLeftArrow = (ImageButton) findViewById(R.id.left_arrow);

		addEventListeners();

	}

	/* Configure event listeners for all components. */
	private void addEventListeners() {

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

	/* Hide button, and select a random arrow to make visible. */
	private void showRandomArrow() {

		// Remove button
		mTapHereButton.setVisibility(View.GONE);

		int random = new Random().nextInt(2);

		if (random == 0) {
			// Show right arrow
			mRightArrow.setVisibility(View.VISIBLE);
		}
		else {
			// Show left arrow
			mLeftArrow.setVisibility(View.VISIBLE);
		}

	}


	/* Reset to initial app state. Button is visible, arrows are gone */
	private void reset() {

		// Remove arrows by setting visibility to 'gone'
		mRightArrow.setVisibility(View.GONE);
		mLeftArrow.setVisibility(View.GONE);

		// And show button
		mTapHereButton.setVisibility(View.VISIBLE);

	}

}
