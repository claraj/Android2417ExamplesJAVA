package com.clara.simple_todo_list_with_fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoItemDetailFragment extends Fragment {

	MarkItemAsDoneListener mItemDoneListener;

	//Factory method for creating new Fragments. Useful place to add arguments for data sent to fragments
	ToDoItemDetailFragment newInstance() {
		return new ToDoItemDetailFragment();
	}

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
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_to_do_item_detail, container, false);
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


