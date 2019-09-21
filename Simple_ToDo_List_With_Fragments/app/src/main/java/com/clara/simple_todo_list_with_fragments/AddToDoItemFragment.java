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
 * A Fragment for adding a new To Do item to the list
 */

public class AddToDoItemFragment extends Fragment {

	public interface NewItemCreatedListener {
		void newItemCreated(ToDoItem newItem);
	}

	private static final String TAG = "Add To Do Item Fragment";

	private NewItemCreatedListener mNewItemListener;

	//Nothing happens here except creating a new Fragment
	//but this is nice to have in case we did need to send any data to this Fragment in the future
	public static AddToDoItemFragment newInstance() {
		return new AddToDoItemFragment();
	}


	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);

		Log.d(TAG, "onAttach");

		if (context instanceof NewItemCreatedListener){
			mNewItemListener = (NewItemCreatedListener) context;
			Log.d(TAG, "set listener");

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

				//Validate user has entered some text
				if (newItemText.getText().length() > 0) {
					String text = newItemText.getText().toString();
					boolean urgent = urgentCheckbox.isChecked();

					//Clear input form
					newItemText.getText().clear();
					urgentCheckbox.setChecked(false);

					//Create a new to do item
					ToDoItem newItem = new ToDoItem(text, urgent);

					Log.d(TAG, "New item is " + newItem);

					//Call listener to notify a newItem was created
					mNewItemListener.newItemCreated(newItem);

				} else {
					Toast.makeText(getActivity(), "Please enter some text", Toast.LENGTH_LONG).show();
				}
			}
		});

		return view;

	}
}
