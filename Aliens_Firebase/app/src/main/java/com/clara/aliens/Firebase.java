package com.clara.aliens;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


// Created by clara on 9/28/16. Handles interaction with Firebase.

class Firebase {

	private static final String ALL_SCORES_KEY = "all_high_scores";
	private static final String TAG = "FIREBASE";
	private ArrayList<HighScore> highScores;
	private DatabaseReference dbReference;

	private LocalStorage localStorage;     //Local high score storage

	interface HighScoreUpdateListener {
		void highScoresUpdated(ArrayList<HighScore> scores);
	}

	Firebase(LocalStorage localStorage) {

		this.localStorage = localStorage;
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		dbReference = database.getReference();

	}

	//TODO - if user plays game for the first time online, and then connects to the internet,
	//FIXME - their high score won't be written to Firebase until they beat that score.

	//If new high score for this user, replace old score with new score.
	void saveHighScore(HighScore highScore, boolean updateExisting) {

		if (updateExisting) {

			String thisUserKey = localStorage.getFirebaseKey();

			Log.d(TAG, "Updating score for user with existing key: " + thisUserKey);

			if (thisUserKey != null) {
				dbReference.child(ALL_SCORES_KEY).child(thisUserKey).setValue(highScore);
				return;
			}
		}

		//If we are adding new, or key not found in local storage, add new

		Log.d(TAG, "Creating new record for user and saving new key");

		DatabaseReference ref = dbReference.child(ALL_SCORES_KEY).push();
		ref.setValue(highScore);
		localStorage.writeFirebaseKey(ref.getKey());

	}


	void getSortedHighScores(final HighScoreUpdateListener highScoreListener) {

		Log.d(TAG, "Fetching scores from firebase");

		//Query - all scores, sort by score, which sorts lowest to highest.
		//High score table will display the 20 highest scores, not everything.
		//As the data is sorted, this is the last 20 results, so limit to the last 20
		//Sorting in reverse order is currently not supported.
		Query allHighScores = dbReference.child(ALL_SCORES_KEY).orderByChild("score").limitToLast(20);

		allHighScores.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				Log.d(TAG, "onDataChange " + dataSnapshot);

				highScores = new ArrayList<>();

				for (DataSnapshot ds : dataSnapshot.getChildren()) {
					
					Log.d(TAG, "ds: " + ds);
					
					HighScore score = ds.getValue(HighScore.class);
					highScores.add(0, score);   // Add to list in reverse order to end up
					                            // with highest score first
				}

				//Notify listener that the scores are ready to display
				highScoreListener.highScoresUpdated(highScores);

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e(TAG, "Get sorted high scores error " + databaseError);
			}
		});
	}


}
