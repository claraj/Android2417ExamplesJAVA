package com.clara.aliens;

import android.os.Parcel;
import android.os.Parcelable;

// Represents a score for a user, from one round of the game.
// Used by Firebase to marshall/unmarshall data from the high score database

public class GameScore implements Parcelable {

	private int score;
	private String username;

	GameScore(String username, int score) {
		this.username = username;
		this.score = score;
	}

	//Empty constructor, and getter and setter methods, required by Firebase

	public GameScore() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String toString() {
		return username + " " + score;
	}



	//Things required to make GameScore Parcelable so a score can be sent from one Fragment to another

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(score);
		parcel.writeString(username);
	}

	protected GameScore(Parcel in) {
		score = in.readInt();
		username = in.readString();
	}

	public static final Creator<GameScore> CREATOR = new Creator<GameScore>() {
		@Override
		public GameScore createFromParcel(Parcel in) {
			return new GameScore(in);
		}

		@Override
		public GameScore[] newArray(int size) {
			return new GameScore[size];
		}
	};

}

