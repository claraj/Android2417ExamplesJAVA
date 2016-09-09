package com.clara.simple_todo_list_with_fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToDoItemFragment extends Fragment {

	private static final String TAG = "Add To Do Item Fragment";

	private NewItemCreatedListener mNewItemlistener;

	public AddToDoItemFragment() {
		// Required empty public constructor
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		try {
			mNewItemlistener = (NewItemCreatedListener) context;
		} catch (ClassCastException cce) {
			throw new ClassCastException(context.toString() + " must implement NewItemCreatedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_add_to_do_item, container, false);

		Button addItem = (Button) view.findViewById(R.id.add_item_button);
		final TextView newItemText = (TextView) view.findViewById(R.id.new_todo_item_edittext);
		final CheckBox urgentCheckbox = (CheckBox) view.findViewById(R.id.urgent_checkbox);

		addItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Validate user has entered data
				String text = newItemText.getText().toString();
				boolean urgent = urgentCheckbox.isChecked();
				ToDoItem newItem = new ToDoItem(text, urgent);

				//Return newItem back to calling Activity
				mNewItemlistener.newItemCreated(newItem);

			}
		});

		return view;

	}

	@Override
	public void onDetach() {
		super.onDetach();
		mNewItemlistener = null;
	}


	public interface NewItemCreatedListener {
		void newItemCreated(ToDoItem newItem);
	}


}
