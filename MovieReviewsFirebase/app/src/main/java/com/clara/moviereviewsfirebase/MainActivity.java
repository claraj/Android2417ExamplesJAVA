package com.clara.moviereviewsfirebase;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

	EditText mMovieNameET, mMovieReviewTextET, mSearchMovieET;
	RatingBar mMovieStars;
	Button mSaveButton, mShowLatestReviewButton, mShowAllReviewsButton, mSearchMovieButton;
	TextView mLatestMovieTV, mMovieSearchTV, mAllMoviesTV;


	private static final String TAG = "Movie Review Activity";

	private static final String ALL_REVIEWS_KEY = "all_reviews";
	private DatabaseReference reviewsReference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//References for components
		mSaveButton = (Button) findViewById(R.id.save_review_button);
		//mShowLatestReviewButton = (Button) findViewById(R.id.show_latest_review_button);

		mSearchMovieButton = (Button) findViewById(R.id.search_movie_review_button);  // not needed as UI updates automatically
		//mShowAllReviewsButton = (Button) findViewById(R.id.show_all_reviews_button);  // not needed as UI updates automatically

		mMovieNameET = (EditText) findViewById(R.id.movie_name_et);
		mMovieReviewTextET = (EditText) findViewById(R.id.movie_review_et);
		mSearchMovieET = (EditText) findViewById(R.id.search_name_et);

		mMovieStars = (RatingBar) findViewById(R.id.movie_stars_rb);

		mLatestMovieTV = (TextView) findViewById(R.id.latest_movie_data_tv);
		mMovieSearchTV = (TextView) findViewById(R.id.search_movie_result_tv);
		mAllMoviesTV = (TextView) findViewById(R.id.all_movie_data_tv);

		addListeners();

		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference dbReference = database.getReference();
		reviewsReference = dbReference.child(ALL_REVIEWS_KEY);

		fetchAllReviews();
		fetchLatestReview();

	}

	private void saveReview() {

		String name = mMovieNameET.getText().toString();
		String reviewText = mMovieReviewTextET.getText().toString();
		float stars = mMovieStars.getRating();

		// Make a new MovieReview object from the data
		MovieReview review = new MovieReview(name, reviewText, stars);

		// Create a new child reference of reviewReference
		DatabaseReference newReviewReference = reviewsReference.push();
		// Set the value of this child to the review object
		// Firebase will convert the data in this object to key-value pairs
		newReviewReference.setValue(review);

		Toast.makeText(this, "Review saved!", Toast.LENGTH_SHORT).show();

	}


	private void fetchLatestReview() {

		reviewsReference.limitToLast(1).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				Log.d(TAG, "Latest Review snapshot: " + dataSnapshot.toString());

				// Even though we've requested only one result, still have to loop over the results

				MovieReview latestReview = null;

				for (DataSnapshot child : dataSnapshot.getChildren()) {
					latestReview = child.getValue(MovieReview.class);
				}

				String latestMovieText = (latestReview == null) ? "No movies reviewed" : latestReview.toString();
				mLatestMovieTV.setText(latestMovieText);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e(TAG, "Firebase error fetching latest review", databaseError.toException());
			}
		});
	}


	// Save data in a list to preserve order.
	// Can use this approach if order is important

	private void fetchAllReviews() {

		// Provide the name of a key that all the children have; to sort by that key
		reviewsReference.orderByChild("name").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				Log.d(TAG, "All reviews in name order: " + dataSnapshot.toString());

				ArrayList<MovieReview> allReviews = new ArrayList<MovieReview>();

				for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
					MovieReview review = childSnapshot.getValue(MovieReview.class);
					allReviews.add(review);
				}
				mAllMoviesTV.setText(allReviews.toString());
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e(TAG, "Firebase error fetching all reviews", databaseError.toException());
			}
		});

	}


	/*

	  // Fetch all reviews, marshalls data into a HashMap.
	  // Can use this approach if it doesn't matter what order the results of a list are in

	private void fetchAllReviews() {

		reviewsReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				Log.d(TAG, "All reviews: " + dataSnapshot.toString());

				// Specify how to deserialize the returned data. The empty {} at the end of this line are necessary
				GenericTypeIndicator<HashMap<String, MovieReview>> movieMapType
						= new GenericTypeIndicator<HashMap<String, MovieReview>>() {};

				// Get the data from the snapshot, and convert it to the desired type
				// The order of items is *not* guaranteed
				HashMap<String, MovieReview> reviews = dataSnapshot.getValue(movieMapType);

				// If the key had no data, reviews will be null
				if (reviews != null) {
					mAllMoviesTV.setText(reviews.values().toString());
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e(TAG, "Firebase error fetching all reviews", databaseError.toException());
			}
		});

	}

	*/


	private void searchReviews() {

		// Read the data in the search ET and query Firebase
		String search = mSearchMovieET.getText().toString();
		// Have to sort by the child key first, then apply equalTo filter
		reviewsReference.orderByChild("name").equalTo(search).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				Log.d(TAG, "Review search data: " + dataSnapshot);

				ArrayList<MovieReview> matches = new ArrayList<>();
				for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
					MovieReview review = childSnapshot.getValue(MovieReview.class);
					matches.add(review);
				}

				String text = (matches.isEmpty()) ? "No matches" : matches.toString();
				mMovieSearchTV.setText(text);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e(TAG, "Firebase error searching reviews", databaseError.toException());
			}
		});

	}




	private void addListeners() {

		//Register listeners for buttons

		mSaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				saveReview();
			}
		});


		mSearchMovieButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchReviews();
			}
		});





	}


}
