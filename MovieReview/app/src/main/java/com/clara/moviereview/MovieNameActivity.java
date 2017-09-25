package com.clara.moviereview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

public class MovieNameActivity extends AppCompatActivity {

	public static final String MOVIE_NAME_EXTRA = "MovieReviewActivity_movieName_extra";
	public static final String INITIAL_MOVIE_NAME_EXTRA = "Initial Movie Name Provided By Launching Activity";

	Button mSaveButton;
	EditText mMovieNameEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_name);


		mMovieNameEditText = (EditText) findViewById(R.id.movieNameEditText);
		mSaveButton = (Button) findViewById(R.id.saveNameButton);

		// Was an initial movie name sent by the launching Activity?
		String initialMovieName = getIntent().getStringExtra(INITIAL_MOVIE_NAME_EXTRA);
		mMovieNameEditText.setText(initialMovieName);

		mSaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveMovieName();
			}
		});

	}

	private void saveMovieName() {

		// Is something entered in EditText? Error Toast and return if not.

		String name = mMovieNameEditText.getText().toString();

		if (name.isEmpty()) {
			Toast.makeText(this, "Enter a movie name", Toast.LENGTH_LONG).show();
			return;
		}

		// Otherwise, create an Intent to carry the name back to the launching Activity

		Intent returnDataIntent = new Intent();   // Create intent
		returnDataIntent.putExtra(MOVIE_NAME_EXTRA, name);   // add name, as Extra
		setResult(RESULT_OK, returnDataIntent);    //
		finish();   // And stop this Activity. The Activity that launched this one will be re-created and it's onActivityResult will be called.

	}
}
