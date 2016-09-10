package com.clara.simple_todo_list_with_fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

		Log.d(TAG, "onCreate instance state as follows:" + savedInstanceState);

		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			//no saved instance state - first time Activity been created
			//Create new arraylist, add fragments.

			mTodoItems = new ArrayList<>();

			Log.d(TAG, "onCreate, array list is = " + mTodoItems);

			AddToDoItemFragment addNewFragment = AddToDoItemFragment.newInstance();
			ToDoListFragment listFragment = ToDoListFragment.newInstance(mTodoItems);

			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			ft.add(R.id.add_todo_view_container, addNewFragment, ADD_NEW_FRAG_TAG);
			ft.add(R.id.todo_list_view_container, listFragment, LIST_FRAG_TAG);

			ft.commit();

		} else {

			//There is saved instance state, so the app has already run,
			// and the Activity should already have fragments.

			Log.d(TAG, "onCreate saved instance state is " + savedInstanceState);

			//Restore saved instance state, the ArrayList
			mTodoItems = savedInstanceState.getParcelableArrayList(TODO_ITEMS_KEY);
			Log.d(TAG, "onCreate arraylist =  " + mTodoItems);


		}

	}

	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		outBundle.putParcelableArrayList(TODO_ITEMS_KEY, mTodoItems);

	}

	@Override
	public void newItemCreated(ToDoItem newItem) {

		//Send item to list
		mTodoItems.add(newItem);

		//get reference to list fragment and tell it that the data set has changed
		FragmentManager fm = getFragmentManager();
		ToDoListFragment listFrag = (ToDoListFragment) fm.findFragmentByTag(LIST_FRAG_TAG);  //TODO error handling
		listFrag.notifyItemsChanged();

	}

	@Override
	public void itemSelected(ToDoItem selected) {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		//ft.replace(R.id.todo_manager_view, ToDoItemDetailFragment.newInstance(selected));
		ft.replace(android.R.id.content, ToDoItemDetailFragment.newInstance(selected));

		ft.addToBackStack(DETAIL_FRAG_TAG);
		ft.commit();

	}

	@Override
	public void todoItemDone(ToDoItem doneItem) {

		//Remove item from list
		mTodoItems.remove(doneItem);

		//Find list fragment and tell it that the list data has changed
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		FragmentManager fm = getFragmentManager();

		ToDoListFragment listFrag = (ToDoListFragment) fm.findFragmentByTag(LIST_FRAG_TAG);
		listFrag.notifyItemsChanged();

		Log.d(TAG, " backstack count = " + fm.getBackStackEntryCount());

		//Go back to prev Fragment - the add+list view
		fm.popBackStack();
		ft.commit();

	}
}
