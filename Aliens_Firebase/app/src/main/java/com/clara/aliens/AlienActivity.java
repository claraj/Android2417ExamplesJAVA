package com.clara.aliens;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

//TODO fix layout for welcome screen fragment
//todo fix layout for high score fragment
//todo local storage for score and username
//todo vibrate on alien demise (test on device)

//todo firebase - get high score data, send high score data if new high score


public class AlienActivity extends AppCompatActivity implements WelcomeFragment.UsernameListener, GameFragment.EndGameListener, HighScoresFragment.RestartListener {

	String username;
	ArrayList<HighScore> highScores;
	Firebase firebase;
	LocalStorage localStorage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alien);

		firebase = new Firebase();
		localStorage = new LocalStorage(this);

		fetchUsername(); //todo fetch from firebase or device storage or whatever.
		fetchHighScores();  //Also todo, fetch from firebase

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		WelcomeFragment welcomeFragment = WelcomeFragment.newInstance(username);
		ft.add(R.id.activity_alien, welcomeFragment);
		ft.commit();
	}

	private void fetchHighScores() {
		//todo - talk to Firebase
		highScores = new ArrayList<>();
	}



	private void fetchUsername() {
		//// TODO
		username = localStorage.fetchUsername();
	}


	private void startGame() {
		GameFragment gameFragment = GameFragment.newInstance();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.activity_alien, gameFragment);
		ft.commit();
	}


	@Override
	public void endGame(int score) {

		//Show high scores

		if (score > localStorage.getHighScore()) {
			localStorage.writeHighScore(score);
			firebase.sendHighScore(username, score);
		}

		highScores = firebase.getSortedHighScores();

		HighScoresFragment highScoresFragment = HighScoresFragment.newInstance(highScores);

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
