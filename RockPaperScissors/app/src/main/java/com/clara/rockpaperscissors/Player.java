package com.clara.rockpaperscissors;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by clara on 11/2/16.  For deserializing Firebase data
 */
public class Player {

	boolean available;
	String played;       // rock, paper, scissors, or null

	@Exclude
	String key;

	Player() {}

	Player(boolean avail, String played, String key) {
		this.available = avail;
		this.played = played;
		this.key = key;
	}



	@Override
	public String toString() {
		return "Player{" +
				"available=" + available +
				", played='" + played + '\'' +
				", key='" + key + '\'' +
				'}';
	}
}
