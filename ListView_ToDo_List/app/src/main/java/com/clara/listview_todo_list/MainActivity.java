package com.clara.listview_todo_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	// An ArrayList for the ListView's data source
	ArrayList<String> todoItems = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button addNewButton = (Button) findViewById(R.id.new_todo_item_button);
		final EditText newToDoEditText = (EditText) findViewById(R.id.new_todo_item_edittext);

		ListView todoListView = (ListView) findViewById(R.id.todo_listview);

		final ArrayAdapter<String> todoListAdapter = new ArrayAdapter<String>(this, R.layout.todo_list_item, R.id.todo_item_text, todoItems);

		todoListView.setAdapter(todoListAdapter);

		addNewButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Read whatever user has typed into newToDoEditText and add it to the list
				String newItem = newToDoEditText.getText().toString();
				todoItems.add(newItem);
				//Clear EditText, ready to type in next item
				newToDoEditText.getText().clear()
			}
		});




	}
}
