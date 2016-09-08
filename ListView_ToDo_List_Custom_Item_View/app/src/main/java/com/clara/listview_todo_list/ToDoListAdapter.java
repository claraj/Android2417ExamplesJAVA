package com.clara.listview_todo_list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {

	Context context;

	//ArrayList<ToDoItem> toDoItemArrayList;

	public ToDoListAdapter(Context context, int resource, List<ToDoItem> data) {
		super(context, resource);
		this.context = context;
		//toDoItemArrayList = new ArrayList<ToDoItem>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			View row = inflater.inflate(R.layout.todo_list_item, parent, false);
		}

		ToDoItem item = getItem(position);

		// = toDoItemArrayList.get(position);

		TextView todoText = (TextView) convertView.findViewById(R.id.todo_item_text);
		TextView todoDate = (TextView) convertView.findViewById(R.id.todo_item_created_date);

		todoText.setText(item.getText());
		todoDate.setText(item.getCreated().toString());

		return convertView;

	}
}
