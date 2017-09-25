package com.clara.moviereview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MovieReviewActivity extends AppCompatActivity {

	public static final String MOVIE_STAR_RATING_EXTRA = "Movie Star Rating";
	public static final String MOVIE_WOULD_SEE_AGAIN_EXTRA = "Would see again";

	public static final String INITIAL_STAR_RATING = "Initial star rating";
	public static final String INITIAL_WOULD_SEE_AGAIN = "Initial would see again";
	public static final String INITIAL_MOVIE_NAME = "Initial movie name";

	CheckBox mSeeAgainCheckBox;
	RatingBar mRatingBar;
	Button mSaveReviewButton;
	TextView mMovieName;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_review);


		// Find UI components
		mSeeAgainCheckBox = (CheckBox) findViewById(R.id.seeAgainCheckBox);
		mRatingBar = (RatingBar) findViewById(R.id.movieReviewRatingBar);
		mSaveReviewButton = (Button) findViewById(R.id.saveReviewButton);
		mMovieName = (TextView) findViewById(R.id.movieNameToReviewTextView);

		// Get initial values from the launch Intent, if provided
		float starRating = getIntent().getFloatExtra(INITIAL_STAR_RATING, 0);    // If data is not provided, 0 is the default
		boolean wouldSeeAgain = getIntent().getBooleanExtra(INITIAL_WOULD_SEE_AGAIN, false);   // If data is not provided, false is the default
		String name = getIntent().getStringExtra(INITIAL_MOVIE_NAME);

		// And use these to configure components
		mRatingBar.setRating(starRating);
		mSeeAgainCheckBox.setChecked(wouldSeeAgain);
		mMovieName.setText(name);


		mSaveReviewButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveReview();
			}
		});

	}

	private void saveReview() {

		// What rating did the user give the movie, as a number of stars
		float starRating = mRatingBar.getRating();

		// Find out if user would see again
		boolean wouldSeeAgain = mSeeAgainCheckBox.isChecked();

		// Create an Intent to carry this data back to the launching Activity
		Intent returnData = new Intent();

		// Add the star rating as an extra
		returnData.putExtra(MOVIE_STAR_RATING_EXTRA, starRating);

		// And add the would see again boolean as an extra
		returnData.putExtra(MOVIE_WOULD_SEE_AGAIN_EXTRA, wouldSeeAgain);

		// Provide the Intent (containing the data as extras) as the result from this Activity
		setResult(RESULT_OK, returnData);

		// And end this Activity
		finish();

	}




}
