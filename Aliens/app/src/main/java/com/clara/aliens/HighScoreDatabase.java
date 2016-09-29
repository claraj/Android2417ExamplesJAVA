package com.clara.aliens;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by admin on 9/29/16.
 */

public class HighScoreDatabase {

	ArrayList<HighScore> highScores;

	public HighScoreDatabase() {

		highScores = new ArrayList<>();

		//Some example scores for testing
		highScores.add(new HighScore("Someone", 10000));
		highScores.add(new HighScore("Someone else", 1000));

	}


	//If new high score for this user, replace old score with new score.
	public void saveHighScore(HighScore highScore) {

		for (HighScore s : highScores) {
			if (s.getUsername().equals(highScore.getUsername())) {
				if (s.score < highScore.getScore()) {
					s.setScore(highScore.getScore());
					return;
				}
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
