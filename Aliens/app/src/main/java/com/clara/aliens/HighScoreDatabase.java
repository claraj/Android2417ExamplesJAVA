package com.clara.aliens;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by admin on 9/29/16.
 */

public class HighScoreDatabase {

	private static final String TAG = "HIGH SCORE DB";
	ArrayList<HighScore> highScores;

	public HighScoreDatabase() {

		highScores = new ArrayList<>();l

		//Some example scores for testing
		highScores.add(new HighScore("Someone", 10000));
		highScores.add(new HighScore("Someone else", 1000));
		highScores.add(new HighScore("And someone else", 100));


	}


	//If new high score for this user, replace old score with new score.
	public void saveHighScore(HighScore highScore) {

		for (HighScore s : highScores) {
			if (s.getUsername().equals(highScore.getUsername())) {
				if (s.score < highScore.getScore()) {
					s.setScore(highScore.getScore());
					Log.i(TAG, "Updating score for " + highScore);

				}
				return;
			}
		}

		//If we get here, the user is not in the list of high scores.
		highScores.add(highScore);

	}

	public ArrayList<HighScore> getSortedHighScores() {

		Collections.sort(highScores);
		return highScores;

	}


}
