package com.clara.aliens;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Clara on 9/29/16.
 */

public class HighScore implements Comparable<HighScore>, Parcelable {


	public HighScore(String username, int score) {
		this.username = username;
		this.score = score;
	}

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

	int score;
	String username;

	@Override
	public int compareTo(HighScore otherHighScore) {
		return Integer.valueOf(otherHighScore.getScore()).compareTo(this.score);
	}

	public String toString() {
		return username + " " + score;
	}



	//Things required to make HighScore Parcelable

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(score);
		parcel.writeString(username);
	}

	protected HighScore(Parcel in) {
		score = in.readInt();
		username = in.readString();
	}

	public static final Creator<HighScore> CREATOR = new Creator<HighScore>() {
		@Override
		public HighScore createFromParcel(Parcel in) {
			return new HighScore(in);
		}

		@Override
		public HighScore[] newArray(int size) {
			return new HighScore[size];
		}
	};

}

