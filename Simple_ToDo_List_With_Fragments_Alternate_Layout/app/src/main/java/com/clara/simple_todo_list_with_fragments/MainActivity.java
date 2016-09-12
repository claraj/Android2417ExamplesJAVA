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

		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			//no saved instance state - first time Activity been created
			//Create new ArrayList, and add Add and List fragments.
			Log.d(TAG, "onCreate has no instance state. Set up ArrayList, add List Fragment, Detail, Add fragment");

			mTodoItems = new ArrayList<>();

			AddToDoItemFragment addNewFragment = AddToDoItemFragment.newInstance();
			ToDoListFragment listFragment = ToDoListFragment.newInstance(mTodoItems);
			ToDoItemDetailFragment detailFragment = ToDoItemDetailFragment.newInstance(new ToDoItem("", false));

			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			ft.add(R.id.add_todo_view_container, addNewFragment, ADD_NEW_FRAG_TAG);
			ft.add(R.id.todo_list_view_container, listFragment, LIST_FRAG_TAG);
			ft.add(R.id.todo_detail_view_container, detailFragment);

			ft.commit();

		} else {
			//There is saved instance state, so the app has already run,
			//and the Activity should already have fragments.
			//Restore saved instance state, the ArrayList

			mTodoItems = savedInstanceState.getParcelableArrayList(TODO_ITEMS_KEY);
			Log.d(TAG, "onCreate has saved instance state ArrayList =  " + mTodoItems);
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		outBundle.putParcelableArrayList(TODO_ITEMS_KEY, mTodoItems);
	}



	@Override
	public void newItemCreated(ToDoItem newItem) {

		//Add item to the ArrayList
		mTodoItems.add(newItem);

		Log.d(TAG, "newItemCreated =  " + mTodoItems);

		//get reference to list Fragment from the FragmentMananger,
		// and tell this Fragment that the data set has changed
		FragmentManager fm = getFragmentManager();
		ToDoListFragment listFragment = (ToDoListFragment) fm.findFragmentByTag(LIST_FRAG_TAG);
		listFragment.notifyItemsChanged();
	}




	@Override
	public void itemSelected(ToDoItem selected) {

		//Replace the previous Detail fragment with a new one, showing the selected To Do item
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.todo_detail_view_container, ToDoItemDetailFragment.newInstance(selected));
		ft.commit();

	}


	@Override
	public void todoItemDone(ToDoItem doneItem) {

		mTodoItems.remove(doneItem);
		//Find List fragment and tell it that the  data has changed
		FragmentManager fm = getFragmentManager();
		ToDoListFragment listFragment = (ToDoListFragment) fm.findFragmentByTag(LIST_FRAG_TAG);
		listFragment.notifyItemsChanged();

	}


}
