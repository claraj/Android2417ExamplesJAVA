package com.clara.simple_todo_list_with_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * Displays a RecyclerView list of to do items. Responds to list updates.
 */

public class ToDoListFragment extends Fragment {

	private ListItemSelectedListener mItemSelectedListener;

	private static final String TAG = "TODO LIST FRAGMENT" ;

	//todo create newInstance method to send arguments to this Fragment

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

		//todo Configure ListView with list of ToDo items
		//todo Use custom ToDoListArrayAdapter

		return view;
	}

	//todo create a method for notifying this Fragment about list updates

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof ListItemSelectedListener) {
			mItemSelectedListener = (ListItemSelectedListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mItemSelectedListener = null;
	}

	public interface ListItemSelectedListener {
		void itemSelected(ToDoItem selected);
	}
}
