package com.clara.rockpaperscissors.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

/**
 * Created by clara on 11/2/16.  For deserializing Firebase data
 */
public class Player implements Parcelable {

	public boolean available;
	public String played;       // rock, paper, scissors, or null

	@Exclude
	public String key;


	//What stage of the game is the player in? available, ready, played, viewing results, reset?
	@Exclude
	String state;

	Player() {}

	public Player(boolean avail, String played, String key) {
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


	//Parcelable stuff

	Player(Parcel in) {
		available = in.readByte() != 0;
		played = in.readString();
		key = in.readString();
		state = in.readString();
	}

	public static final Creator<Player> CREATOR = new Creator<Player>() {
		@Override
		public Player createFromParcel(Parcel in) {
			return new Player(in);
		}

		@Override
		public Player[] newArray(int size) {
			return new Player[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeByte((byte) (available ? 1 : 0));
		parcel.writeString(played);
		parcel.writeString(key);
		parcel.writeString(state);
	}
}
