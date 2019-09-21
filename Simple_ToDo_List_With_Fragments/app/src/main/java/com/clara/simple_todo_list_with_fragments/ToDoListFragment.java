package com.clara.simple_todo_list_with_fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Displays a list of to do items. Responds to list updates.
  */

public class ToDoListFragment extends Fragment implements ToDoListAdapter.OnListItemClickListener {

	public interface ListItemSelectedListener {
		void itemSelected(ToDoItem selected);
	}

	private ListItemSelectedListener mItemSelectedListener;

	private static final String TAG = "TODO LIST FRAGMENT" ;
	private static final String ARGS_TODO_LIST = "to do list arguments";

	private RecyclerView mListView;
	private ToDoListAdapter mListAdapter;
	private List<ToDoItem> mListItems;


	public static ToDoListFragment newInstance(ArrayList<ToDoItem> todoItems) {
		final Bundle args = new Bundle();
		args.putParcelableArrayList(ARGS_TODO_LIST, todoItems);
		final ToDoListFragment fragment = new ToDoListFragment();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public void onAttach(@NonNull Context context) {

		Log.d(TAG, "onAttach");

		super.onAttach(context);

		if (context instanceof ListItemSelectedListener) {
			mItemSelectedListener = (ListItemSelectedListener) context;
			Log.d(TAG, "On attach configured listener " + mItemSelectedListener);
		} else {
			throw new RuntimeException(context.toString() + " must implement ListItemSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.d(TAG, "onCreateView");

		View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

		mListView =  view.findViewById(R.id.to_do_listview);
		mListView.setHasFixedSize(true);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
		mListView.setLayoutManager(layoutManager);

		mListItems = new ArrayList<>();

		if (getArguments() != null) {
			mListItems = getArguments().getParcelableArrayList(ARGS_TODO_LIST);
		}

		mListAdapter = new ToDoListAdapter(mListItems, this);
		mListView.setAdapter(mListAdapter);
		mListAdapter.notifyDataSetChanged();

		Log.d(TAG, "onCreateView, ArrayList: " + mListItems);

		return view;
	}


	@Override
	public void onListItemClick(int itemPosition) {
		ToDoItem item = mListItems.get(itemPosition);
		mItemSelectedListener.itemSelected(item);
	}


	public void notifyItemsChanged() {
		this.mListView.getAdapter().notifyDataSetChanged();
	}



}
