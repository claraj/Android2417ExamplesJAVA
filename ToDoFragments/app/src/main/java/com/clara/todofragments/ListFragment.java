package com.clara.todofragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class ListFragment extends Fragment {

	private ListView toDoListView;
	private ArrayAdapter<ToDoItem> toDoArray;

	private Button addNewItem;

	private OnListItemSelectedListener selectedListener;
	private NewItemCallback newItemListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_to_do_list, group, false);
		toDoListView = (ListView) v.findViewById(R.id.to_do_list_view);


		toDoListView.setAdapter(toDoArray);
		toDoListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectedListener.onListItemSelected(toDoArray.getItem(position));
			}
		});


		addNewItem = (Button) v.findViewById(R.id.add_new_todo_item);
		addNewItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//This should tell the Activity to launch a new fragment to add the new item.
				newItemListener.onNewItemRequest();
			}
		});


		return v;

	}

	private void addTestData() {

		toDoArray.add("Write programs");
		toDoArray.add("Walk the dog");
		toDoArray.add("Buy milk");
	}


	void addNewItem(String newItem) {

		toDoArray.add(newItem);
		toDoArray.notifyDataSetChanged();

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		toDoArray = new ArrayAdapter<>(this.getActivity(), R.layout.list_view_to_do_item, R.id.list_item);
		addTestData();

		try {
			selectedListener = (OnListItemSelectedListener) context;
			newItemListener = (NewItemCallback) context;
		} catch (ClassCastException cce) {
			throw new ClassCastException(context.toString() + " must implement all required listeners");
		}
	}

	public interface OnListItemSelectedListener {
		void onListItemSelected(String toDoItem);
	}

	public interface OnNewItemRequestListener {
		void onNewItemRequest();
	}


}
