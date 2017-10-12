package com.clara.hellosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {

	private Context context;
	private SQLHelper helper;
	private SQLiteDatabase db;
	protected static final String DB_NAME = "products.db";

	protected static final int DB_VERSION = 1;
	protected static final String DB_TABLE = "inventory";

	private static final String ID_COL = "_id";
	protected static final String NAME_COL = "product_name";
	protected static final String QUANTITY_COL = "quantity";

	private static final String DB_TAG = "DatabaseManager" ;
	private static final String SQL_TAG = "SQLHelper" ;

	public DatabaseManager(Context c) {
		this.context = c;
		helper = new SQLHelper(c);
		this.db = helper.getWritableDatabase();
	}

	public void close() {
		helper.close(); // Closes the database - very important to ensure all data is saved!
	}


	//TODO add method to fetch all data and return a Cursor
	//TODO add method to select (search) for a product by name
	//TODO add method to insert (add) a product and quantity
	//TODO add method to delete a product
	//TODO add method to update (change) the quantity of a product


	public class SQLHelper extends SQLiteOpenHelper {
		public SQLHelper(Context c){
			super(c, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			//Table contains a primary key column, _id which autoincrements - saves you setting the value
			//Having a primary key column is almost always a good idea. In this app, the _id column is used by
			//the list CursorAdapter data source to figure out what to put in the list, and to uniquely identify each element
			//Name column, String
			//Quantity column, int

			String createTable = "CREATE TABLE " + DB_TABLE +
					" (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					NAME_COL +" TEXT UNIQUE, " + QUANTITY_COL +" INTEGER);"  ;

			Log.d(SQL_TAG, createTable);
			db.execSQL(createTable);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(db);
			Log.w(SQL_TAG, "Upgrade table - drop and recreate it");
		}
	}
}

