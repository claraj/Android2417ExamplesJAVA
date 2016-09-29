package com.clara.aliens;

import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


//todo vibrate on alien demise (test on device)

//todo firebase - get high score data, send high score data if new high score


public class AlienActivity extends AppCompatActivity implements WelcomeFragment.UsernameListener, GameFragment.EndGameListener, HighScoresFragment.RestartListener {

	String username;

	LocalStorage localStorage;
	HighScoreDatabase highScoreDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//for testing - uncomment to clear saved username and high score
		//PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alien);

		localStorage = new LocalStorage(this);
		highScoreDB = new HighScoreDatabase();

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
	public void endGame(int score) {

		//Check to see if user has new high score. If so, save new high score to local storage.

		//Fetch previous high score
		int lastHighScore = localStorage.getHighScore();


		// User has never played game before. Since this is their first score, any score is a high score.

		if (lastHighScore == LocalStorage.NO_SCORE_RECORDED) {
			localStorage.writeHighScore(score);
			highScoreDB.saveHighScore(new HighScore(username, score));
		}

		else {

			// User has played game before. This score is greater than the previous high score

			if (score > lastHighScore) {
				highScoreDB.saveHighScore(new HighScore(username, score));
				localStorage.writeHighScore(score);

			}

			// User has played game before. This score is not greater than the previous high score

			else  {
				//This takes care of the situation where user has played game before and does not beat previous high score
				highScoreDB.saveHighScore(new HighScore(username, lastHighScore));
				localStorage.writeHighScore(score);
			}
		}

		//And send all high scores to the HighScoreFragment to display

		HighScoresFragment highScoresFragment = HighScoresFragment.newInstance(highScoreDB.getSortedHighScores());

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
