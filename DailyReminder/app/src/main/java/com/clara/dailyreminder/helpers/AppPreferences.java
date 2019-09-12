package com.clara.dailyreminder.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.clara.dailyreminder.ui.ConfigurationActivity;

/**
 * Created by admin on 9/22/17.
 */

public class AppPreferences {

	private static final String PREF_STORE_NAME = "com.clara.dailyreminders.helpers.reminder_preferences";

	private static final String USER_TASK_PREFERENCE_KEY = "user task";


	public static void saveTask(String task, Context c) {

		// Only accessible by this app
		SharedPreferences preferences = c.getSharedPreferences(PREF_STORE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(USER_TASK_PREFERENCE_KEY, task);
		editor.apply();
	}



	public static String getTask(Context c) {
		SharedPreferences preferences = c.getSharedPreferences(PREF_STORE_NAME, Context.MODE_PRIVATE);
		String task = preferences.getString(USER_TASK_PREFERENCE_KEY, null);
		return task;
	}

	public static void saveRepeatInterval(long mInterval, Context c) {
		// Only accessible by this app
		SharedPreferences preferences = c.getSharedPreferences(PREF_STORE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(USER_TASK_PREFERENCE_KEY, mInterval);
		editor.apply();

	}
}
