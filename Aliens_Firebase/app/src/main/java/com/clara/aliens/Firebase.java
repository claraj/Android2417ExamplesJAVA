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


		FirebaseAuth f_auth = FirebaseAuth.getInstance();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference reference = database.getReference();

	}


	//If new high score for this user, replace old score with new score.
	public void sendHighScore(String username, int score) {

		for (HighScore s : highScores) {
			if (s.getUsername().equals(username)) {
				if (s.score < score) {
					s.setScore(score);
				}
			}
		}
	}

	public ArrayList<HighScore> getSortedHighScores() {
		//// TODO - these are not sorted yet
		Collections.sort(highScores);
		return highScores;
	}

	/* Local version - one way to sort the scores is to make the HighScore class implement Comparable<HighScore> interface, and
	provide a compareTo method. Then you can simply call Collections.sort on the highScores array.
	 */


}
