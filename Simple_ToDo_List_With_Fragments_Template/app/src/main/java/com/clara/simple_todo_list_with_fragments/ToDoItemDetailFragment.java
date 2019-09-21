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


	//TODO create newInstance method, with one argument, the ToDoItem to show

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

		// TODO Get the To Do item from the arguments passed in when this Fragment was created.
		// TODO call setTodoItem to set data in view components


		return view;
	}

	public void setTodoItem(ToDoItem item) {

		// TODO set data in view components
		// TODO add Done button event listener - will send message to MarkItemAsDoneListener

	}



}
