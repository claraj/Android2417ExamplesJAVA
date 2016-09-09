package com.clara.simple_todo_list_with_fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoItemDetailFragment extends Fragment {


	MarkItemAsDoneListener mItemDoneListener;

	public ToDoItemDetailFragment() {
		// Required empty public constructor
	}

	@Override
	public void onAttach(Context context) {
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
		return inflater.inflate(R.layout.fragment_to_do_item_detail, container, false);

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
