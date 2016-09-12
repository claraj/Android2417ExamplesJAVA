package com.clara.hellosqlite;

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

public class ProductsActivity extends AppCompatActivity {

	//Simple app which uses a SQLite Database
	//Product info database
	//User types in a product name (string) and quantity in stock (int) and presses a button to save
	//App records these in a database
	//App has a search button to look for a specific product
	//And another button which fetches and shows all products
	//TODO delete a product  -  long press on list
	//TODO update a product quantity - add button

	EditText productNameET;
	EditText productQuantityET;
	EditText searchNameET;
	EditText updateProductQuantityET;

	TextView productSearchTV;
	ListView allProductsListView;
	ArrayAdapter<Product> allProductsListAdapter;

	Button addProductButton;
	Button searchProductsButton;
	Button updateQuantityButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);

		//TODO create database

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

		//TODO fetch all data from DB and display in ListView
		updateProductsListView();

		addProductButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Add this new product to the database
				String newName = productNameET.getText().toString();
				String newQuantity = productQuantityET.getText().toString();

				if ( newName.length() == 0 || newQuantity.length() == 0) {
					Toast.makeText(ProductsActivity.this, "Please enter both a product name and quantity", Toast.LENGTH_LONG).show();
					return;
				}

			}

		});


		searchProductsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String searchName = searchNameET.getText().toString();
				if ( searchName.equals("")) {
					Toast.makeText(ProductsActivity.this, "Please enter a product to search for", Toast.LENGTH_LONG).show();
					return;
				}
				//TODO Otherwise, find name from DB and display name and phone number in displaySearchproductTV
			}

		});


		updateQuantityButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO ensure a product name and quantity are provided
			}
		});


		allProductsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				//TODO show confirmation, then delete item
				return false;
			}
		});


	}

	private void updateProductsListView() {
		//TODO fetch all data from DB and display in ListView
	}


}
