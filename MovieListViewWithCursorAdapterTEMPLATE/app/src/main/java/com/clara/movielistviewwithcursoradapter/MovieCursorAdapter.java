package com.clara.movielistviewwithcursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


public class MovieCursorAdapter extends CursorAdapter {

	private static final String TAG = "MOVIE CURSOR ADAPTER";
	RatingChangedListener ratingChangedListener;

	//Correpond to column numbers in database
	private static final int ID_COL = 0;
	private static final int MOVIE_COL = 1;
	private static final int RATING_COL = 2;

	public MovieCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);

		//Convert context to RatingChangedListener, just like adding a listener to a Fragment
		if (context instanceof RatingChangedListener) {
			this.ratingChangedListener = (RatingChangedListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement RatingChangedListener");
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.movie_list_item, parent, false);
		return v;
	}

	@Override
	public void bindView(View view, Context context, final Cursor cursor) {

		//TODO get references to components that will contain data from database

		//TODO register listener for user changing RatingBar to change rating for this movie
		//TODO If user changes rating, notify the RatingChangedListener

	}


	interface RatingChangedListener {
		void notifyRatingChanged(int movieID, float newRating);
	}
}
