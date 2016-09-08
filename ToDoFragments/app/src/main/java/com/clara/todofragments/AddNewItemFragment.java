package com.clara.todofragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddNewItemFragment extends Fragment {

	Button saveButton;
	EditText details;

	NewItemCallback newItemListener;


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			newItemListener = (NewItemCallback) context;
		} catch (ClassCastException cce) {
			throw new ClassCastException(context.toString() + " must implement NewIemCallback interface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_add_new_to_do_item, group, false);
		details = (EditText) view.findViewById(R.id.new_todo_text);

		saveButton = (Button) view.findViewById(R.id.save_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Get text from EditText and send back to host Activity
				String newText = details.getText().toString();
				ToDoItem newItem = new ToDoItem(newText);
				newItemListener.newItem(newItem);

			}
		});

		return view;

	}

}

interface NewItemCallback {
	public void newItem(ToDoItem newItem);
}