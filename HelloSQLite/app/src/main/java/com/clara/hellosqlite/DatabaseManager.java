package com.clara.hellosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {

	private Context context;
	private SQLHelper helper;
	private SQLiteDatabase db;

	protected static final String DB_NAME = "products";
	protected static final int DB_VERSION = 1;
	protected static final String DB_TABLE = "inventory";

	protected static final String nameCol = "product_name";
	protected static final String quantityCol = "quantity";

	private static final String DBTAG = "DatabaseManager" ;
	private static final String SQLTAG = "SQLHelper" ;

	public DatabaseManager(Context c) {
		this.context = c;
		helper = new SQLHelper(c);
		this.db = helper.getWritableDatabase();
	}

	public void close() {
		helper.close(); //Closes the database - very important!
	}

	//TODO add method to fetch all data
	//TODO add method to search for a product by name
	//TODO add method to add a product and quantity
	//TODO add method to delete a product
	//TODO add method to update the quantity of a product

	public class SQLHelper extends SQLiteOpenHelper {
		public SQLHelper(Context c){
			super(c, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String createTable = "CREATE TABLE " + DB_TABLE + " (" + nameCol +" TEXT, " + quantityCol +" INTEGER);"  ;
			db.execSQL(createTable);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(db);
			Log.w(SQLTAG, "Upgrade table - drop and recreate it");
		}
	}
}

