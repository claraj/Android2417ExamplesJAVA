package com.clara.simple_todo_list_with_fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToDoListFragment.ListItemSelectedListener} interface
 * to handle interaction events.
  */

public class ToDoListFragment extends Fragment {

	private static final String TAG = "TODO LIST FRAGMENT" ;
	private static final String TODO_LIST = "todo list saved inatance state arg";
	private static final String TODO_LIST_ARGS = "to do list arguments";
	private ListItemSelectedListener mListener;

	private ListView mListView;
	private ToDoListArrayAdapter mListAdapter;
	private ArrayList<ToDoItem> mListItems;


	public static ToDoListFragment newInstance(ArrayList todoItems) {
		final Bundle args = new Bundle();
		args.putParcelableArrayList(TODO_LIST_ARGS, todoItems);
		final ToDoListFragment fragment = new ToDoListFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

		Log.d(TAG, "This fragment has arguments: " + getArguments());

		mListView = (ListView) view.findViewById(R.id.to_do_listview);
		mListItems = getArguments().getParcelableArrayList(TODO_LIST_ARGS);

		mListAdapter = new ToDoListArrayAdapter(getActivity(), R.layout.todo_list_item_list_element, mListItems);

		mListView.setAdapter(mListAdapter);
		mListAdapter.notifyDataSetChanged();

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d(TAG, "List item " + position + " clicked");
				mListener.itemSelected(mListAdapter.getItem(position));
			}
		});

		return view;

	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof ListItemSelectedListener) {
			mListener = (ListItemSelectedListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public void notifyItemsChanged() {
		Log.d(TAG, "rec'd add/modify item message");
		mListAdapter.notifyDataSetChanged();
	}


	public interface ListItemSelectedListener {
		void itemSelected(ToDoItem selected);
	}
}
