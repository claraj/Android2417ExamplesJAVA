package com.clara.simple_todo_list_with_fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Fragment to show detail about one To Do item. Has 'Done' button to mark item as done.
 */


public class ToDoItemDetailFragment extends Fragment {

	private static final String TODO_ITEM_ARGUMENT = "todo item argument";
	private static final String TAG = "TODO ITEM DETAIL FRAG";

	MarkItemAsDoneListener mItemDoneListener;

	//todo create newInstance method to send arguments to this Fragment

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof MarkItemAsDoneListener) {
			mItemDoneListener = (MarkItemAsDoneListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement MarkItemAsDoneListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_to_do_item_detail, container, false);

		//TODO set up this Fragment to display the correct data for one ToDo item
		//TODO event handler for Done button

		return view;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mItemDoneListener = null;
	}


	interface MarkItemAsDoneListener {
		void todoItemDone(ToDoItem doneItem);
	}
}



