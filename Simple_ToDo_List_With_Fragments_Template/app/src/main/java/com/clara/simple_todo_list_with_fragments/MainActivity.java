package com.clara.simple_todo_list_with_fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
		AddToDoItemFragment.NewItemCreatedListener,
		ToDoItemDetailFragment.MarkItemAsDoneListener,
		ToDoListFragment.ListItemSelectedListener {


	private static final String TODO_ITEMS_KEY = "TODO ITEMS ARRAY LIST";
	private static final String ADD_NEW_FRAG_TAG = "ADD NEW FRAGMENT";
	private static final String LIST_FRAG_TAG = "LIST FRAGMENT";
	private static final String DETAIL_FRAG_TAG = "DETAIL FRAGMENT";

	private ArrayList<ToDoItem> mTodoItems;

	private static final String TAG = "MAIN ACTIVITY";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//TODO if no saved instance state, then app has just started
		//TODO create ArrayList, create Add and List fragments and add to this Activity

		//TODO else, if there is saved instance state, then app is currently running
		//TODO get ArrayList from saved instance state

	}

	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		//TODO save ArrayList in outBundle
	}

	@Override
	public void newItemCreated(ToDoItem newItem) {
		//todo Add new item to list, notify ToDoListFragment that it needs to update
	}

	@Override
	public void itemSelected(ToDoItem selected) {
		//todo Show ToDoDetailFragment for this Todo item
	}

	@Override
	public void todoItemDone(ToDoItem doneItem) {
		//todo remove from ArrayList.
		//todo If showing ToDoItemDetailFragment, go back to List+Add view.
	}
}


