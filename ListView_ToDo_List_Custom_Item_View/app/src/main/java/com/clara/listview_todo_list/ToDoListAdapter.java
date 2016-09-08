package com.clara.listview_todo_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by admin on 9/1/16.
 */
public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {

	public ToDoListAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		LayoutInflater inflater = (Activity)
	}
}
