package com.clara.aliens;

import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


//  todo, appearance:
// 	custom list adapter/list view to display high scores in table form
//	if no data connection, a better message for the high score table in HighScoreFragment.


// todo, firebase
// Test with more than one device
// user plays game. User loses data connection. User beats previous high score. Will Firebase record of score be updated?


public class AlienActivity extends AppCompatActivity implements
		WelcomeFragment.UsernameListener,
		GameFragment.EndGameListener,
		HighScoresFragment.RestartListener {

	String username;

	LocalStorage localStorage;
	Firebase highScoreDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//for testing - uncomment to clear saved username and high score
		//PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alien);

		localStorage = new LocalStorage(this);
		highScoreDB = new Firebase(localStorage);

		username = localStorage.fetchUsername();  //Will be null if no username has been saved.

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		WelcomeFragment welcomeFragment = WelcomeFragment.newInstance(username);
		ft.add(R.id.activity_alien, welcomeFragment);
		ft.commit();

	}


	private void startGame() {
		GameFragment gameFragment = GameFragment.newInstance();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.activity_alien, gameFragment);
		ft.commit();
	}


	@Override
	public void endGame(int thisGameScore) {

		//Check to see if user has new high score. If so, save new high score to local storage.

		//Fetch previous high score
		int previousHighScore = localStorage.getHighScore();

		// User has never played game before. Since this is their first score, any score is a high score.

		if (previousHighScore == LocalStorage.NO_SCORE_RECORDED) {
			localStorage.writeHighScore(thisGameScore);
			HighScore newHighScore = new HighScore(username, thisGameScore);
			highScoreDB.saveHighScore(newHighScore, false);          //updateExisting = false. In other words, write new record to DB.
		}

		// Else, user has played before. Did they beat their previous high score?

		else {

			// User has played game before. This score is greater than the previous high score

			if (thisGameScore > previousHighScore) {
				HighScore newHighScore = new HighScore(username, thisGameScore);
				highScoreDB.saveHighScore(newHighScore, true);			//updateExisting = true, update an existing record.
				localStorage.writeHighScore(thisGameScore);

			}

			// User has played game before. This score is not greater than the previous high score

			else  {

				//Placeholder for user not beating a previous high score.

			}
		}


		//Create new HighScoresFragment. Send the score from this game to be displayed.
		HighScoresFragment highScoresFragment = HighScoresFragment.newInstance(new HighScore(username, thisGameScore));
		//Request Firebase gets top high scores and sends to the HighScoreFragment to display
		highScoreDB.getSortedHighScores(highScoresFragment);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.activity_alien, highScoresFragment);
		ft.commit();

	}

	@Override
	public void playAgain() {

		startGame();

	}

	@Override
	public void userStartsPlay(String username) {

		this.username = username;

		//write to local storage
		localStorage.writeUsername(username);

		startGame();

	}


}
