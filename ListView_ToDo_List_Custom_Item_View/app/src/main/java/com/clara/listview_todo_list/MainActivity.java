package com.clara.listview_todo_list;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	final static String TAG = "com.clara.listview_todo_list.MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button addNewButton = (Button) findViewById(R.id.new_todo_item_button);
		final EditText newToDoEditText = (EditText) findViewById(R.id.new_todo_item_edittext);

		ListView todoListView = (ListView) findViewById(R.id.todo_listview);

		//Create ArrayAdapter
		final ToDoListAdapter todoListAdapter = new ToDoListAdapter(this, R.layout.todo_list_item);

		//Configure the ListView to use this Adapter for data
		todoListView.setAdapter(todoListAdapter);

		//Add listener to the button, to add items to the ListView
		addNewButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//Read whatever user has typed into newToDoEditText
				String newItemText = newToDoEditText.getText().toString();

				//Make sure some data was entered. Show error Toast and return if not
				if (newItemText.length() == 0) {
					Toast.makeText(MainActivity.this, "Enter a todo item", Toast.LENGTH_SHORT).show();
					return;
				}

				//Else, create a new ToDoIem from the text, and add to the ArrayAdapter
				ToDoItem newItem = new ToDoItem(newItemText);
				todoListAdapter.add(newItem);

				//And notify the ArrayAdapter that the data set has changed, to request UI update
				todoListAdapter.notifyDataSetChanged();

				//Clear EditText, ready to type in next item
				newToDoEditText.getText().clear();

			}
		});


		//This listener responds to long presses on individual list items
		todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			//Remove the item at position in ArrayList, but first ask user if they are sure
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				Log.d(TAG, "On long click listener");
				final int indexPosition = position;    //Copy position clicked into final variable so it can be used inside the Dialog's event handler
				final ToDoItem item = todoListAdapter.getItem(position);

				AlertDialog areYouSureDialog = new AlertDialog.Builder(MainActivity.this)
					.setTitle("Delete this item?")
					.setMessage(item.getText())
					.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//remove item from ArrayList
							todoListAdapter.remove(item);
							//And notify Adapter of changes
							todoListAdapter.notifyDataSetChanged();
						}
					})
					.setNegativeButton(R.string.no, null) /* no listener needed, if user presses this button the dialog will just close and nothing will happen */
					.create();

			areYouSureDialog.show();

			return true; //Does this event handler completely handle this event?
			//Would return false if you wanted this event to then be passed onto other listeners that may receive this event
		}
		});
	}
}



