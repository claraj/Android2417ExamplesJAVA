package com.clara.simple_todo_list_with_fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * Fragment to show detail about one To Do item.
 * Has 'Done' button to mark item as done.
 */

public class ToDoItemDetailFragment extends Fragment {


	interface MarkItemAsDoneListener {
		void todoItemDone(ToDoItem doneItem);
	}

	private MarkItemAsDoneListener mItemDoneListener;

	private static final String ARG_TODO_ITEM = "todo item argument";
	private static final String TAG = "TODO ITEM DETAIL FRAG";

	private TextView textText;
	private TextView dateText;
	private CheckedTextView urgentCheck;
	private Button doneButton;


	//Use in place of a constructor. Customize the arguments for this Fragment as needed.
	// onCreateView will be able to access the arguments.
	public static ToDoItemDetailFragment newInstance(ToDoItem item) {
		final Bundle args = new Bundle();
		args.putParcelable(ARG_TODO_ITEM, item);
		final ToDoItemDetailFragment fragment = new ToDoItemDetailFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);

		Log.d(TAG, "onAttach");

		if (context instanceof MarkItemAsDoneListener) {
			mItemDoneListener = (MarkItemAsDoneListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement MarkItemAsDoneListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView");

		View view = inflater.inflate(R.layout.fragment_to_do_item_detail, container, false);

		//Get the To Do item from the arguments passed in when this Fragment was created.

		if (getArguments() != null && getArguments().getParcelable(ARG_TODO_ITEM) != null){

			final ToDoItem item = getArguments().getParcelable(ARG_TODO_ITEM);
			Log.d(TAG, "onCreateView received the following item: " + item);

			textText = view.findViewById(R.id.to_do_detail_text_textview);
			dateText =  view.findViewById(R.id.to_do_detail_date_created_textview);
			urgentCheck = view.findViewById(R.id.to_do_detail_urgent_checkbox);
			doneButton =  view.findViewById(R.id.to_do_detail_done_button);

			setTodoItem(item);

 		} else {
			Log.w(TAG, "Did not receive a ToDoItem");
		}

		return view;
	}

	public void setTodoItem(ToDoItem item) {

		final ToDoItem toDoItem = item;

		textText.setText(item.getText());
		dateText.setText(item.getFormattedDateCreated());

		if (item.isUrgent()) {
			urgentCheck.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
		} else {
			urgentCheck.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
		}

		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Tell the MarkItemAsDoneListener that this item is done
				mItemDoneListener.todoItemDone(toDoItem);
			}
		});

	}



}
