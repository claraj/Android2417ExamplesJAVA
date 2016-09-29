package com.clara.aliens;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by admin on 9/28/16.
 */


public class LocalStorage {

	private static final String USERNAME_KEY = "username";
	private static final String HIGHSCORE_KEY = "highscore";
	private static final String TAG = "LOCAL STORAGE";

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
		return preferences.getInt(HIGHSCORE_KEY, -1);

	}


}
