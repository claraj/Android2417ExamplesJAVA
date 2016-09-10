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
public class AddToDoItemFragment extends Fragment {

	private static final String TAG = "Add To Do Item Fragment";

	private NewItemCreatedListener mNewItemlistener;

	//Factory method for creating new Fragments. Useful place to add arguments for data sent to fragments
	AddToDoItemFragment newInstance() {
		return new AddToDoItemFragment();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof NewItemCreatedListener) {
			mNewItemlistener = (NewItemCreatedListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement NewItemCreatedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_add_to_do_item, container, false);
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
