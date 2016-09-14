package com.clara.hellosqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


public class ProductListAdapter<Product> extends CursorAdapter {

	Context context;

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

		TextView productListName = (TextView) view.findViewById(R.id.product_list_text);
		productListName.setText(cursor.getString(1) + " " + cursor.getInt(2));

	}
}
