package com.clara.aliens;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by admin on 9/28/16.
 */
public class Firebase {

	private static final String ALL_SCORES_KEY = "all_high_scores";
	private static final String TAG = "FIREBASE";
	ArrayList<HighScore> highScores;

	DatabaseReference dbReference;

	LocalStorage localStorage;

	public interface HighScoreUpdateListener {
		void highScoresUpdated(ArrayList<HighScore> scores);
	}

	public Firebase(LocalStorage localStorage) {

		this.localStorage = localStorage;
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		dbReference = database.getReference();

	}


	//If new high score for this user, replace old score with new score.
	public void saveHighScore(HighScore highScore, boolean updateExisting) {

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


	public void getSortedHighScores(final HighScoreUpdateListener highScoreListener) {

		Log.d(TAG, "Fetching scores from firebase");

		Query allHighScores = dbReference.child(ALL_SCORES_KEY).orderByChild("score").limitToLast(10);

		allHighScores.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				Log.d(TAG, "onDataChange " + dataSnapshot);

				highScores = new ArrayList<HighScore>();

				for (DataSnapshot ds : dataSnapshot.getChildren()) {
					
					Log.d(TAG, "ds: " + ds);
					
					HighScore score = ds.getValue(HighScore.class);
					highScores.add(0, score);       // Add in reverse order
				}


				highScoreListener.highScoresUpdated(highScores);

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.e(TAG, "Get sorted high scores error " + databaseError);
			}
		});

	}


}
