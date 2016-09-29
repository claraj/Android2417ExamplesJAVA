package com.clara.aliens;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by admin on 9/28/16.
 */
public class Firebase {

	ArrayList<HighScore> highScores;

	public Firebase() {

		highScores = new ArrayList<>();

		//Some example scores for testing
		highScores.add(new HighScore("Someone", 10000));
		highScores.add(new HighScore("Someone else", 1000));

		//TODO configure Firebase

		FirebaseAuth f_auth = FirebaseAuth.getInstance();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference reference = database.getReference();

	}


	//If new high score for this user, replace old score with new score.
	public void saveHighScore(HighScore highScore) {

		//TODO

	}

	public ArrayList<HighScore> getSortedHighScores() {

		//// TODO - fetch from Firebase

		return highScores;

	}


}
