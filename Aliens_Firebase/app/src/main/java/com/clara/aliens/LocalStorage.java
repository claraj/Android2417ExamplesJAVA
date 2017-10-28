package com.clara.aliens;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by clara on 9/28/16.
 *
 *
 * Use SharedPreferences - a persistent storage area, private to this app - to store username and their highest mScore.
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
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString(USERNAME_KEY, username).apply();
	}


	protected String fetchUsername() {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(USERNAME_KEY, null);
	}


	protected void writeHighScore(int newScore) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(HIGHSCORE_KEY, newScore).apply();
	}


	protected int getHighScore() {
		// By default, it returns 0 if no scores found. For this game, -1 is a better the default if no mScore exists.
		// since 0 is a valid mScore. -1 can be differentiated from valid scores.
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(HIGHSCORE_KEY, NO_SCORE_RECORDED);
	}


	public String getFirebaseKey() {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(FIREBASE_KEY, null);
	}


	public void writeFirebaseKey(String key) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString(FIREBASE_KEY, key).apply();
	}


}
