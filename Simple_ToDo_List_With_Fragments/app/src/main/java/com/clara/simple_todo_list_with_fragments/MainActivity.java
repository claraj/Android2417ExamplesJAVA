package com.clara.simple_todo_list_with_fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements AddToDoItemFragment.NewItemCreatedListener, ToDoItemDetailFragment.MarkItemAsDoneListener, ToDoListFragment.ListItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public void newItemCreated(ToDoItem newItem) {
		//todo
	}

	@Override
	public void itemSelected(ToDoItem selected) {
		//todo
	}

	@Override
	public void todoItemDone(ToDoItem doneItem) {
		//todo
	}
}
