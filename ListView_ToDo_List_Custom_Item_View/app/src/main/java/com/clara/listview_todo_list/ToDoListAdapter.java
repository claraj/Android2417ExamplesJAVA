package com.clara.listview_todo_list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ToDoListAdapter extends ArrayAdapter<ToDoItem> {

	Context context;    // Often the Activity that this ListView belongs to

	public ToDoListAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
	}

	// This method is used to create a view for a given list item. It's a convenient
	// place to insert the data about one particular list item into the its own view.

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;

		// Android recycles views as the list scrolls. If the view is shown for the first
		// time, or if Android can't find it for some reason it needs to be inflated from the layout.
		// If Android already has a reference to the View, it's provided as convertView parameter
		// so you don't need to inflate it again
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.todo_list_item, parent, false);
		}

		// In either case, you have a View to display information for one list item,
		// and you need to add your data to it

		//Get the correct ToDoItem
		ToDoItem item = getItem(position);

		//Find the UI elements in the view
		TextView todoText = (TextView) rowView.findViewById(R.id.todo_item_text);
		TextView todoDate = (TextView) rowView.findViewById(R.id.todo_item_created_date);

		//And set their values
		todoText.setText(item.getText());
		todoDate.setText(item.getCreated().toString());

		//Return the row, to be displayed in the ListView.
		return rowView;

	}
}




