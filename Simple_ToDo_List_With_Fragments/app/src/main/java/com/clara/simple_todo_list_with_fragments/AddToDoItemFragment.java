package com.clara.simple_todo_list_with_fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * Fragment for adding a new To Do item to the list
 */

public class AddToDoItemFragment extends Fragment {

	public interface NewItemCreatedListener {
		void newItemCreated(ToDoItem newItem);
	}

	private static final String TAG = "Add To Do Item Fragment";

	private NewItemCreatedListener mNewItemListener;

	//Nothing happens here except creating a new Fragment
	//but this is useful to have in case of needing to send data to this Fragment in the future
	public static AddToDoItemFragment newInstance() {
		return new AddToDoItemFragment();
	}


	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);

		Log.d(TAG, "onAttach");

		// Context is the hosting Activity. Set the listener to this Activity.
		if (context instanceof NewItemCreatedListener){
			mNewItemListener = (NewItemCreatedListener) context;
			Log.d(TAG, "Listener set");
		} else  {
			throw new RuntimeException(context.toString() + " must implement NewItemCreatedListener");
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_add_to_do_item, container, false);

		Log.d(TAG, "onCreateView");

		Button addItem = view.findViewById(R.id.add_todo_item_button);
		final EditText newItemText = view.findViewById(R.id.new_todo_item_edittext);
		final CheckBox urgentCheckbox = view.findViewById(R.id.urgent_checkbox);

		addItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String text = newItemText.getText().toString();

				//Validate user has entered some text
				if (text.isEmpty()) {
					Toast.makeText(getActivity(), "Please enter some text", Toast.LENGTH_LONG).show();
					return;
				}

				boolean urgent = urgentCheckbox.isChecked();

				//Clear input form
				newItemText.getText().clear();
				urgentCheckbox.setChecked(false);

				//Create a new to do item
				ToDoItem newItem = new ToDoItem(text, urgent);

				Log.d(TAG, "New item is " + newItem);

				//Call listener's newItemCreated method to notify it that a newItem was created
				mNewItemListener.newItemCreated(newItem);

			}
		});

		return view;

	}
}
