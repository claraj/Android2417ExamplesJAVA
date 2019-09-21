package com.clara.simple_todo_list_with_fragments;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;


public class ToDoItem implements Parcelable {

	private static DateFormat df = DateFormat.getDateInstance();

	private String text;
	private Date dateCreated;
	private boolean urgent;

	public ToDoItem(String text, boolean urgent) {
		this.text = text;
		this.urgent = urgent;
		dateCreated = new Date();
	}

	public ToDoItem(Parcel in) {    // Constructor needed for un-parceling ToDoItem objects
		text = in.readString();
		dateCreated = (Date) in.readSerializable();
		urgent = in.readInt() == 1;  // true if a 1 was written, false otherwise
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getFormattedDateCreated() {
		return df.format(dateCreated);          // Convenience method to return a String in the format "September 20 2019"
	}

	public boolean isUrgent() {
		return urgent;
	}

	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}

	@NonNull
	@Override
	public String toString() {
		return text + " " + dateCreated + " is urgent? " + urgent;
	}


	// Code required by the Parcelable interface.
	// If ToDoItem is parcelable, can send as an Extra between Fragments/Activities

	static final Parcelable.Creator<ToDoItem> CREATOR = new Parcelable.Creator<ToDoItem>() {
		public ToDoItem createFromParcel(Parcel in) {
			return new ToDoItem(in);
		}

		@Override
		public ToDoItem[] newArray(int size) {
			return new ToDoItem[size];
		}
	};


	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(text);
		dest.writeSerializable(dateCreated);
		dest.writeInt( urgent ? 1 : 0);   // Write 1 if urgent == true, 0 otherwise
	}
}
