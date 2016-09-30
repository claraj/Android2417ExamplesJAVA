package com.clara.aliens;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by clara on 9/28/16.
 *
 *
 * Use SharedPreferences - a persistent storage area, private to this app - to store username and their highest score.
 * SharedPreferences is a key-value store.
 *
 */


public class LocalStorage {

	private static final String USERNAME_KEY = "username";
	private static final String HIGHSCORE_KEY = "highscore";
	private static final String FIREBASE_KEY = "firebase key";

	private static final String TAG = "LOCAL STORAGE";

	public static final int NO_SCORE_RECORDED = -1;

	private Context context;

	LocalStorage(Context context) {
		this.context = context;
	}

	protected void writeUsername(String username) {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.edit().putString(USERNAME_KEY, username).apply();

	}


	protected String fetchUsername() {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(USERNAME_KEY, null);

	}

	protected void writeHighScore(int newScore) {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.edit().putInt(HIGHSCORE_KEY, newScore).apply();


	}

	protected int getHighScore() {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getInt(HIGHSCORE_KEY, NO_SCORE_RECORDED);    // -1 is the default, if no score exists. So if user gets 0 as a score, it will still register as a new high score.

	}


	public String getFirebaseKey() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(FIREBASE_KEY, null);
	}


	public void writeFirebaseKey(String key) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.edit().putString(FIREBASE_KEY, key).apply();


	}


}
