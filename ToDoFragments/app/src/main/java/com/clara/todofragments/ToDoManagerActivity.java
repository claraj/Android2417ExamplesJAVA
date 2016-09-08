package com.clara.todofragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ToDoManagerActivity extends AppCompatActivity {

	public static final String TODO_DETAIL = "to do item detail text";

	private ToDoListFragment listFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		//Need to add the List fragment

		listFragment =  new ToDoListFragment();

		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		transaction.add(android.R.id.content, listFragment);

		transaction.commit();

	}


	@Override
	public void onListItemSelected(String toDoItemDetail) {

		//User selected item from list.
		//swap fragments, display todoitem detail

		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		ToDoItemFragment itemFragment = new ToDoItemFragment();

		Bundle arguments = new Bundle();
		arguments.putString(TODO_DETAIL, toDoItemDetail);
		itemFragment.setArguments(arguments);

		transaction.addToBackStack(itemFragment.getClass().getName());  //tag for transaction == fragment class name
		transaction.replace(android.R.id.content, itemFragment);
		transaction.commit();

	}

	@Override
	public void onNewItemRequest() {

		//Launch new fragment to enter new todoitem data
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		AddNewToDoItemFragment addFragment = new AddNewToDoItemFragment();
		transaction.addToBackStack(addFragment.getClass().getName());  //Add to back stack but must remove later or will cycle through all previous additions before able to exit
		transaction.replace(android.R.id.content, addFragment);
		transaction.commit();
	}



	@Override
	public void newItemCreated(String newItem) {

		//This is called when a new item has been created and should be added to the list.

		if (newItem == null || newItem.equals("")) {
			Toast.makeText(this, "No text entered, no new item created", Toast.LENGTH_LONG).show();
		}
		else {
			listFragment.addNewItem(newItem);

			//Replace whatever with listitemfragment. Hang onto a reference to this since it won't change? TODO review.

			//Backstack considerations: need to pop AddNewTodDoItemItemFragment off backstack.
			//Also don't need to add ToDoListFragment to backstack - that's the base view and pressing back here should exit

			FragmentManager manager = getFragmentManager();
			manager.popBackStack();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(android.R.id.content, listFragment);
			transaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
