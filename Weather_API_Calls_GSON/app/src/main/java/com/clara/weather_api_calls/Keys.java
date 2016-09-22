package com.clara.weather_api_calls;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Keys {

	//Returns a key, or null if file not found or can't be read
	protected static String getKeyFromRawResource(Context context, int rawResource) {

		//Create a stream reader for this raw resource
		InputStream keyStream = context.getResources().openRawResource(rawResource);
		//And a BufferedReader to read file into lines of text
		BufferedReader keyStreamReader = new BufferedReader(new InputStreamReader(keyStream));
		try {
			//And read one line of text
			String key = keyStreamReader.readLine();
			return key;
		} catch (IOException ioe) {
			return null;
		}

		//TODO - how would you deal with a situation with 4 keys?
		// TODO how could you structure the file?

	}

}
