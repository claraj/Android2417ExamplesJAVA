package com.clara.hellosqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class ProductListAdapter extends CursorAdapter {

	Context context;

	private static int NAME_COL = 1;
	private static int QUANTITY_COL = 2;

	public ProductListAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.list_item, parent, false);
		return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// Put data from this cursor (represents one row of the database) into this
		// view (the corresponding row in the list)



		final TextView productListName = (TextView) view.findViewById(R.id.product_list_name);
		TextView productListQuantity = (TextView) view.findViewById(R.id.product_list_quantity);

//		final String name = cursor.getString(NAME_COL);
//		final String shortVersion = name.substring(0, 4) + "...";
//		productListName.setText(shortVersion);
//
//		view.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				if (productListName.getText().toString().endsWith("...") ){
//					productListName.setText(name);
//				} else {
//					productListName.setText(shortVersion);
//				}
//			}
//		});

		productListName.setText(cursor.getString(NAME_COL));



		productListQuantity.setText(cursor.getString(QUANTITY_COL));




	}
}
