package com.clara.rockpaperscissors;

import com.google.firebase.database.Exclude;

/**
 * Created by admin on 11/3/16.
 */

public class Game {

	String player1key;
	String player2key;
	int player1score;
	int player2score;

	Game() {}

	@Exclude
	String key;

	public Game(String player1key, String player2key, int player1score, int player2score, String key) {
		this.player1key = player1key;
		this.player2key = player2key;
		this.player1score = player1score;
		this.player2score = player2score;
		this.key = key;
	}

	@Override
	public String toString() {
		return "Game{" +
				"player1key='" + player1key + '\'' +
				", player2key='" + player2key + '\'' +
				", player1score=" + player1score +
				", player2score=" + player2score +
				", key='" + key + '\'' +
				'}';
	}
}
