package com.clara.simple_todo_list_with_fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Displays a list of to do items. Responds to list updates.
  */

public class ToDoListFragment extends Fragment implements ToDoListAdapter.OnListItemClickListener {

	private static final String TAG = "TODO LIST FRAGMENT" ;
	private static final String ARGS_TODO_LIST = "to do list arguments";
	private ListItemSelectedListener mItemSelectedListener;

	private RecyclerView mListView;
	private ToDoListAdapter mListAdapter;

	public static ToDoListFragment newInstance(ArrayList<ToDoItem> todoItems) {
		final Bundle args = new Bundle();
		args.putParcelableArrayList(ARGS_TODO_LIST, todoItems);
		final ToDoListFragment fragment = new ToDoListFragment();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

		mListView =  view.findViewById(R.id.to_do_listview);
		mListView.setHasFixedSize(true);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
		mListView.setLayoutManager(layoutManager);

		List<ToDoItem> listItems = new ArrayList<>();

		if (getArguments() != null) {
			listItems = getArguments().getParcelableArrayList(ARGS_TODO_LIST);
		}

		mListAdapter = new ToDoListAdapter(listItems, this);
		mListView.setAdapter(mListAdapter);
		mListAdapter.notifyDataSetChanged();

		Log.d(TAG, "onCreateView, ArrayList: " + listItems);

		return view;
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof ListItemSelectedListener) {
			mItemSelectedListener = (ListItemSelectedListener) context;
			Log.d(TAG, "On attach configured listener " + mItemSelectedListener);

		} else {
			throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
		}
	}


	@Override
	public void onDetach() {
		super.onDetach();
		mItemSelectedListener = null;
	}


	@Override
	public void onListItemClick(ToDoItem item) {
		mItemSelectedListener.itemSelected(item);
	}

	public void notifyItemsChanged() {
		this.mListView.getAdapter().notifyDataSetChanged();
	}



	public interface ListItemSelectedListener {
		void itemSelected(ToDoItem selected);
	}
}
