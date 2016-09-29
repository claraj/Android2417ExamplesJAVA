package com.clara.aliens;

import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

//TODO fix layout for welcome screen fragment
//todo fix layout for high score fragment
//todo vibrate on alien demise (test on device)

//todo firebase - get high score data, send high score data if new high score


public class AlienActivity extends AppCompatActivity implements WelcomeFragment.UsernameListener, GameFragment.EndGameListener, HighScoresFragment.RestartListener {

	String username;

	LocalStorage localStorage;
	HighScoreDatabase highScoreDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//for testing - uncomment to clear saved username and high score
		PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

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

		if (score > localStorage.getHighScore()) {
			localStorage.writeHighScore(score);
			highScoreDB.saveHighScore(new HighScore(username, score));
		}

		//And show high score

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
