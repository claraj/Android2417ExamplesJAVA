package com.clara.aliens;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

// TODO to test, user plays game. User loses data connection. User beats previous high score. Will Firebase record of score be updated?


public class AlienActivity extends AppCompatActivity implements
		WelcomeFragment.UsernameListener,
		GameFragment.EndGameListener,
		HighScoresFragment.RestartListener {

	private static final String TAG = "AlienActivity";

	LocalStorage localStorage;
	Firebase highScoreDB;

	private final String WELCOME_FRAGMENT_TAG = "welcome fragment";
	private final String GAME_FRAGMENT_TAG = "game fragment";
	private final String HIGH_SCORE_FRAGMENT_TAG = "high score fragment";

	private String mGameState;

	private final String GAME_STATE = "game state key";

	private GameFragment gameFragment;  // Have to keep track of gameFragment's state

	protected void onCreate(Bundle savedInstanceState) {

		//for testing - uncomment to clear saved username and high score
		// PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alien);

		Log.d(TAG, "Saved instance state: " + savedInstanceState);

		highScoreDB = new Firebase();

		localStorage = new LocalStorage(this);

		mGameState = WELCOME_FRAGMENT_TAG;   // First time app starts, should be in the WELCOME state

		// If this is a configuration change (e.g. device rotation), there is probably state in the GameFragment or the HighScoreFragment

		if (savedInstanceState != null) {
			// Get the current game state, default is Welcome
			mGameState = savedInstanceState.getString(GAME_STATE, WELCOME_FRAGMENT_TAG);
			gameFragment = (GameFragment) getSupportFragmentManager().getFragment(savedInstanceState, GAME_FRAGMENT_TAG);
		}

		else {
			// First time the app has loaded
		}

		// What fragment was loaded? Re-load the same Fragment

		Log.d(TAG, "Game state is " + mGameState);

		switch (mGameState) {

			case HIGH_SCORE_FRAGMENT_TAG:
				loadHighScoreFragment(null);  // No score to send - if one was sent when the Fragment was created, it should remember it
				break;

			case GAME_FRAGMENT_TAG:
				loadGameFragment();    // Note that the game may be in progress, this Fragment will deal with saving game state and restoring it.
				break;

			case WELCOME_FRAGMENT_TAG:
				// Fall through to loadWelcomeFragment

				default:
				// No fragments loaded, or it wasn't the HighScoreFragment or GameFragment, so show the welcome fragment
				loadWelcomeFragment();
				break;
		}

	}


	private void loadGameFragment() {

		gameFragment = (GameFragment) getSupportFragmentManager().findFragmentByTag(GAME_FRAGMENT_TAG);

		Log.d(TAG, "The game fragment is " + gameFragment);

		if (gameFragment == null) {
			gameFragment = GameFragment.newInstance();
			Log.d(TAG, "Create new game fragment");
		}

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.activity_alien, gameFragment, GAME_FRAGMENT_TAG);
		ft.commit();

	}


	private void loadWelcomeFragment() {

		WelcomeFragment welcomeFragment = (WelcomeFragment) getSupportFragmentManager().findFragmentByTag(WELCOME_FRAGMENT_TAG);

		if (welcomeFragment == null) {
			String username = localStorage.fetchUsername();
			welcomeFragment = WelcomeFragment.newInstance(username);
			Log.d(TAG, "Created new welcome fragment for name " + username );
		}

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.activity_alien, welcomeFragment, WELCOME_FRAGMENT_TAG);
		ft.commit();
	}



	void loadHighScoreFragment(GameScore gameScore) {

		HighScoresFragment highScoreFragment = (HighScoresFragment) getSupportFragmentManager().findFragmentByTag(HIGH_SCORE_FRAGMENT_TAG);

		if (highScoreFragment == null) {
			//Create new HighScoresFragment. Send the score from this game to be displayed.
			highScoreFragment = HighScoresFragment.newInstance(gameScore);
			Log.d(TAG, "Created new high score fragment");
		} else {
			highScoreFragment.setScore(gameScore);
		}

		//Request Firebase fetches top high scores and sends to the HighScoreFragment to display
		highScoreDB.getSortedHighScores(highScoreFragment);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.activity_alien, highScoreFragment, HIGH_SCORE_FRAGMENT_TAG);
		ft.commit();

	}



	@Override
	public void gameEnded(int thisGameScore) {

		mGameState = HIGH_SCORE_FRAGMENT_TAG;

		//Check to see if user has new high score. If so, save new high score to local storage.

		//Fetch previous high score
		int previousHighScore = localStorage.getHighScore();
		String userKey = localStorage.getFirebaseKey();

		GameScore gameScore = new GameScore(localStorage.fetchUsername(), thisGameScore);

		// User has never played game before. Since this is their first score, any score is a high score.

		if (previousHighScore == LocalStorage.NO_SCORE_RECORDED || userKey == null) {
			localStorage.writeHighScore(thisGameScore);
			userKey = highScoreDB.addHighScore(gameScore);
			localStorage.writeFirebaseKey(userKey);
		}

		// Else, user has played before. Did they beat their previous high score?

		else {

			// User has played game before. This score is greater than the previous high score, update local storage

			if (thisGameScore > previousHighScore) {
				highScoreDB.updateHighScore(userKey, gameScore);
				localStorage.writeHighScore(thisGameScore);
			}

			// User has played game before. This score is not greater than the previous high score

			else  {
				//Placeholder for user not beating a previous high score, if there was some logic needed here
			}
		}

		getSupportFragmentManager().beginTransaction().remove(gameFragment).commit();
		loadHighScoreFragment(gameScore);

	}


	@Override
	public void playAgain() {
		mGameState = GAME_FRAGMENT_TAG;
		loadGameFragment();
	}


	@Override
	public void userStartsPlay(String username) {

		//Called when user clicks 'play' button in WelcomeFragment. Write username to local storage

		Log.d(TAG, "Writing username " + username + " to local storage");
		localStorage.writeUsername(username);
		mGameState = GAME_FRAGMENT_TAG;
		loadGameFragment();

	}


	@Override
	public void onSaveInstanceState(Bundle outBundle) {

		super.onSaveInstanceState(outBundle);

		Log.d(TAG, "saving game state " + mGameState);
		outBundle.putString(GAME_STATE, mGameState);

		// If GameFragment is on screen, save it in instance state bundle
		// Reusing the same String to tag the Fragment added to the Activity and the Fragment in the instance state bundle.

		if ( getSupportFragmentManager().findFragmentByTag(GAME_FRAGMENT_TAG) != null) {
			getSupportFragmentManager().putFragment(outBundle, GAME_FRAGMENT_TAG, gameFragment);
		}

	}


}
