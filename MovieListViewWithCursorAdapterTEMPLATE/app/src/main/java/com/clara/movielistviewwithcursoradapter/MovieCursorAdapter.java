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

		TextView nameTV = (TextView) view.findViewById(R.id.movie_title_list_text_view);
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.movie_rating_list_rating_bar);

		nameTV.setText(cursor.getString(MOVIE_COL));
		ratingBar.setRating(cursor.getFloat(RATING_COL));

		final int movie_id = cursor.getInt(ID_COL);

		ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				//This is called any time the rating is changed, including when the view is
				//created and ratingBar.setRating(cursor.getFloat(RATING_COL) is called (above).
				//Don't need to update the database in this event.
				//So, check to see if the change was actually made by the user before requesting DB update.
				if (fromUser) {
					ratingChangedListener.notifyRatingChanged(movie_id, rating);
				}
			}
		});
	}

	interface RatingChangedListener {
		void notifyRatingChanged(int movieID, float newRating);
	}
}
