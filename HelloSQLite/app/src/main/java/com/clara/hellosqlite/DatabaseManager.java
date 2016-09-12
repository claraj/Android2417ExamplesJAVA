package com.clara.hellosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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

	//TODO add method to delete a product
	//TODO add method to update the quantity of a product


	//Add a product and quantity to the database.
	// Returns true if product added, false if product is already in the database
	public boolean addProduct(String name, int quantity) {
		ContentValues newProduct = new ContentValues();
		newProduct.put(nameCol, name);
		newProduct.put(quantityCol, quantity);
		try {
			db.insertOrThrow(DB_TABLE, null, newProduct);
			return true;

		} catch (SQLiteConstraintException sqlce) {
			Log.e(DBTAG, "error inserting data into table. " +
					"Name:" + name + " quantity:" + quantity, sqlce);
			return false;
		}
	}

	public ArrayList<Product> fetchAllProducts() {

		ArrayList<Product> products = new ArrayList<>();

		Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, nameCol);

		while (cursor.moveToNext()) {
			String productName = cursor.getString(0);
			int productQuantity = cursor.getInt(1);
			Product p = new Product(productName, productQuantity);
			products.add(p);
		}

		Log.i(DBTAG, products.toString());

		cursor.close();
		return products;

	}


	//Returns a quantity for a product, or -1 if product is not found in database
	//Not case sensitive
	public int getQuantityForProduct(String productName) {

		String[] cols = { quantityCol };

		// Not case sensitive
		// select quantity from products where upper(name) = upper(<productName>) limit 1

		String selection =  "upper(" + nameCol + ") = upper ( ? ) ";
		String[] selectionArgs = { productName };

		Cursor cursor = db.query(DB_TABLE, cols, selection, selectionArgs, null, null, null, "1");

		if (cursor.getCount() == 1) {
			cursor.moveToFirst();
			int quantity = cursor.getInt(0);
			cursor.close();
			return quantity;
		}

		else {
			return -1;    //todo - better way to indicate product not found?
		}
	}


	public class SQLHelper extends SQLiteOpenHelper {
		public SQLHelper(Context c){
			super(c, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String createTable = "CREATE TABLE " + DB_TABLE + " (" + nameCol +" TEXT UNIQUE, " + quantityCol +" INTEGER);"  ;
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

