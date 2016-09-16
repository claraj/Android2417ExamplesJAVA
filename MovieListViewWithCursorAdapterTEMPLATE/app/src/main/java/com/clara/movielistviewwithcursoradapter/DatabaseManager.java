package com.clara.movielistviewwithcursoradapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {

	private Context context;
	private SQLHelper helper;
	private SQLiteDatabase db;

	protected static final String DB_NAME = "movies";
	protected static final int DB_VERSION = 1;
	protected static final String DB_TABLE = "ratings";

	protected static final String ID_COL = "_id";
	protected static final String MOVIE_NAME_COL = "name";
	protected static final String MOVIE_RATING_COL = "rating";

	private static final String DB_TAG = "DatabaseManager" ;
	private static final String SQLTAG = "SQLHelper" ;

	public DatabaseManager(Context c) {
		this.context = c;
		helper = new SQLHelper(c);
		this.db = helper.getWritableDatabase();
	}

	public void close() {
		helper.close(); //Closes the database - very important!
	}


	public Cursor getAllMovies() {
		//TODO Fetch all data, sort by movie name
		return null; //TODO replace this with a Cursor
	}


	//Add a product and quantity to the database.
	// Returns true if movie added, false if movie is already in the database
	public boolean addMovie(String name, float rating) {
		//TODO add movie
		return false; //TODO replace with boolean reflecting success of operation

	}


	public boolean updateRating(int movieID, float rating) {
		//TODO update the rating for a movie, but movie id.
		return false; //TODO replace with boolean reflecting success of operation
	}


	public class SQLHelper extends SQLiteOpenHelper {
		public SQLHelper(Context c){
			super(c, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String createSQLbase = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT UNIQUE, %s FLOAT )";
			String createSQL = String.format(createSQLbase, DB_TABLE, ID_COL, MOVIE_NAME_COL, MOVIE_RATING_COL);
			db.execSQL(createSQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(db);
			Log.w(SQLTAG, "Upgrade table - drop and recreate it");
		}
	}
}
