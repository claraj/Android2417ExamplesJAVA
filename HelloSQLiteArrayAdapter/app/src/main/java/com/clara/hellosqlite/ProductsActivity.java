package com.clara.hellosqlite;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
	ArrayAdapter<Product> allProductsListAdapter;
	
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

		allProductsListView = (ListView)findViewById(R.id.all_products_listview);
		allProductsListAdapter = new ArrayAdapter<Product>(this, R.layout.list_item);
		allProductsListView.setAdapter(allProductsListAdapter);

		updateProductsListView();


		addProductButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String newName = productNameET.getText().toString();
				String newQuantity = productQuantityET.getText().toString();

				if ( newName.length() == 0  || !newQuantity.matches("^\\d+$")) {   //regex validation
					Toast.makeText(ProductsActivity.this, "Please enter a product name and numerical quantity",
							Toast.LENGTH_LONG).show();
					return;
				}

				int quantity = Integer.parseInt(newQuantity);

				if (dbManager.addProduct(newName, quantity)) {
					Toast.makeText(ProductsActivity.this, "Product added to database", Toast.LENGTH_LONG).show();

					//Clear form and update ListView
					productNameET.getText().clear();
					productQuantityET.getText().clear();
					updateProductsListView();
				} else {
					//Duplicate product name
					Toast.makeText(ProductsActivity.this, newName +" is already in the database",
							Toast.LENGTH_LONG).show();
				}
			}
		});


		searchProductsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String searchName = searchNameET.getText().toString();
				if ( searchName.equals("")) {
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

				//Ensure a product is selected and new quantity provided. The quantity must be a positive integer.

				int newQuantity = -1;  // an invalid value; will be replaced by data entered by user, but only if it is valid

				try {
					newQuantity = Integer.parseInt(updateProductQuantityET.getText().toString());
				} catch (NumberFormatException ne) {
					// Don't modify updateQuantity, so it will stay as -1
				}

				String productName = searchNameET.getText().toString().trim();

				if (productName.length() == 0 || newQuantity >= 0) {
					Toast.makeText(ProductsActivity.this, "Enter a product and a quantity", Toast.LENGTH_SHORT).show();
					return;
				}


				if (dbManager.updateQuantity(productName, newQuantity)){
					Toast.makeText(ProductsActivity.this, "Updated quantity", Toast.LENGTH_LONG).show();
					updateProductsListView();
				} else {
					Toast.makeText(ProductsActivity.this, "Product not found in database", Toast.LENGTH_LONG).show();
				}
			}
		});


		//listview's OnItemLongPressListener to delete product.
		allProductsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				//Show confirmation dialog. If user clicks OK, then delete item
				final Product deleteMe = allProductsListAdapter.getItem(position);

				new AlertDialog.Builder(ProductsActivity.this)
						.setTitle("Delete")
								.setMessage("Delete " + deleteMe.name + "?")
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//If user clicks ok, then delete product
								dbManager.deleteProduct(deleteMe.name);
								updateProductsListView();
								Toast.makeText(ProductsActivity.this, "Product deleted", Toast.LENGTH_LONG).show();

							}
						}).setNegativeButton(android.R.string.cancel, null)  //If user clicks cancel, dismiss dialog, do nothing
						.create().show();

				return false;
			}
		});

	}

	private void updateProductsListView() {

		ArrayList<Product> allProd = dbManager.fetchAllProducts();
		allProductsListAdapter.clear();
		allProductsListAdapter.addAll(allProd);
		allProductsListAdapter.notifyDataSetChanged();
	}


	//override onPause method to close database as Activity pauses
	@Override
	protected void onPause(){
		super.onPause();
		dbManager.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		dbManager = new DatabaseManager(this); //reconnect as Activity restarts
	}


}
