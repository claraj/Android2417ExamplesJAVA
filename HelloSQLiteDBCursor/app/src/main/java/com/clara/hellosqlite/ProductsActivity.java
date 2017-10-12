package com.clara.hellosqlite;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class ProductsActivity extends AppCompatActivity {

	//Simple app which uses a SQLite Database
	//Product info database
	//User types in a product name (string) and quantity in stock (int) and presses a button to save
	//App records these in a database
	//App has a search button to look for a specific product

	EditText productNameET;
	EditText productQuantityET;
	EditText searchNameET;
	EditText updateProductQuantityET;

	ListView allProductsListView;

	ProductListAdapter allProductListAdapter;
	Cursor allProductsCursor;

	Button addProductButton;
	Button searchProductsButton;
	Button updateQuantityButton;

	private DatabaseManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);

		//Create database manager
		dbManager = new DatabaseManager(this);

		productNameET = (EditText)findViewById(R.id.add_new_product_name_et);
		productQuantityET = (EditText)findViewById(R.id.add_new_product_quantity_et);
		searchNameET = (EditText)findViewById(R.id.search_et);
		updateProductQuantityET = (EditText)findViewById(R.id.update_quantity_et);

		addProductButton = (Button)findViewById(R.id.add_product_button);
		searchProductsButton = (Button)findViewById(R.id.search_products_button);
		updateQuantityButton = (Button)findViewById(R.id.update_quantity_button);

		// Get reference to ListView
		allProductsListView = (ListView)findViewById(R.id.all_products_listview);
		// Set up Cursor
		allProductsCursor = dbManager.getCursorAll();
		// Create ProductListAdapter using this Cursor
		allProductListAdapter = new ProductListAdapter(this, allProductsCursor, false);
		// Configure ListView to use this ProductListAdapter
		allProductsListView.setAdapter(allProductListAdapter);

		addProductButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String newName = productNameET.getText().toString();
				String newQuantityString = productQuantityET.getText().toString();
				int quantity = positiveInteger(newQuantityString);

				if ( newName.isEmpty() || quantity < 0 ) {
					Toast.makeText(ProductsActivity.this, "Please enter a product name and numerical" +
							" quantity", Toast.LENGTH_LONG).show();
					return;
				}

				if (dbManager.addProduct(newName, quantity)) {
					Toast.makeText(ProductsActivity.this, "Product added to database",
							Toast.LENGTH_LONG).show();

					//Clear form and update ListView
					productNameET.getText().clear();
					productQuantityET.getText().clear();
					allProductListAdapter.changeCursor(dbManager.getCursorAll());

				} else {
					//Probably a duplicate product name
					Toast.makeText(ProductsActivity.this, newName +" is already in the database",
							Toast.LENGTH_LONG).show();
				}
			}
		});


		searchProductsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String searchName = searchNameET.getText().toString().trim();
				if ( searchName.isEmpty()) {
					Toast.makeText(ProductsActivity.this, "Please enter a product to search for",
							Toast.LENGTH_LONG).show();
					return;
				}

				int quantity = dbManager.getQuantityForProduct(searchName);

				if (quantity == -1) {
					//Product not found
					Toast.makeText(ProductsActivity.this, "Product " + searchName + " not found",
							Toast.LENGTH_LONG).show();
				} else {
					updateProductQuantityET.setText(Integer.toString(quantity));
				}
			}
		});

		updateQuantityButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String newQuantityString = updateProductQuantityET.getText().toString().trim();
				int newQuantity = positiveInteger(newQuantityString);

				String productName = searchNameET.getText().toString();

				if ( productName.isEmpty() || newQuantity < 0) {
					Toast.makeText(ProductsActivity.this, "Please enter a numerical quantity and a product name", Toast.LENGTH_LONG).show();
					return;
				}

				// Call updateQuantity. If update successful, show Toast and update ListView's Adapter's Cursor
				if ( dbManager.updateQuantity(productName, newQuantity) ){
					Toast.makeText(ProductsActivity.this, "Quantity updated", Toast.LENGTH_LONG).show();
					allProductListAdapter.changeCursor(dbManager.getCursorAll());
				} else {
					Toast.makeText(ProductsActivity.this, "Product not found in database", Toast.LENGTH_LONG).show();
				}
			}
		});


		//ListView's OnItemLongClickListener to delete product.
		allProductsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			//The last argument is the value from the database _id column, provided by the ProductListAdapter
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

				//Show confirmation dialog. If user clicks OK, then delete item

				// We can delete by id, no problem, so could simply call dbManager.deleteProduct(id)
				// In this case, we'd like to show a confirmation dialog before deleting,
				// and the dialog will show the the name of the product.
				// So, need to get some data about this list item by calling getItem, to get the Cursor for this row

				Cursor cursor = (Cursor) allProductListAdapter.getItem(position);
				String name = cursor.getString(1);

				new AlertDialog.Builder(ProductsActivity.this)
						.setTitle("Delete")
						.setMessage("Delete " + name + "?")
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//If user clicks ok, then delete product
								dbManager.deleteProduct(id);
								Toast.makeText(ProductsActivity.this, "Product deleted", Toast.LENGTH_LONG).show();
								allProductListAdapter.changeCursor(dbManager.getCursorAll());

							}
						}).setNegativeButton(android.R.string.cancel, null)  //No event handler for negative button.
																// If user clicks cancel, dismiss dialog, do nothing
						.create().show();

				return false;
			}
		});

	}


	/* Utility method to assist with validation.
	Returns a positive int representing the number given by String s.
	For example, "55" returns the int 55
	If the number is negative or cannot be converted to a valid integer, return -1.
	 */
	private int positiveInteger(String s) {
		try {
			int i = Integer.parseInt(s);
			return (i < 0) ? -1 : i;   // if i < 0 then return -1, otherwise return i
		} catch (NumberFormatException ne) {
			return -1;
		}
	}


	//override onPause method to close database as Activity pauses
	@Override
	protected void onPause(){
		super.onPause();
		dbManager.close();
	}

	//reconnect as Activity restarts
	@Override
	protected void onResume() {
		super.onResume();
		dbManager = new DatabaseManager(this);
	}

}
