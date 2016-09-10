package com.clara.simple_todo_list_with_fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;



/**
 * Fragment to show detail about one To Do item. Has 'Done' button to mark item as done.
 */
public class ToDoItemDetailFragment extends Fragment {

	private static final String TODO_ITEM_ARGUMENT = "todo item argument";
	private static final String TAG = "TODO ITEM DETAIL FRAG";

	MarkItemAsDoneListener mItemDoneListener;


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof MarkItemAsDoneListener) {
			mItemDoneListener = (MarkItemAsDoneListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement MarkItemAsDoneListener");
		}
	}

	//Use in place of a constructor
	public static ToDoItemDetailFragment newInstance(ToDoItem item) {
		final Bundle args = new Bundle();
		args.putParcelable(TODO_ITEM_ARGUMENT, item);
		final ToDoItemDetailFragment fragment = new ToDoItemDetailFragment();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_to_do_item_detail, container, false);

		TextView detailTextText = (TextView) view.findViewById(R.id.to_do_detail_text_textview);
		TextView detailDateText = (TextView) view.findViewById(R.id.to_do_detail_date_created_textview);
		CheckBox detailUrgentCheckBox = (CheckBox) view.findViewById(R.id.to_do_detail_urgent_checkbox);
		Button doneButton = (Button) view.findViewById(R.id.to_do_detail_done_button);

		final ToDoItem item = getArguments().getParcelable(TODO_ITEM_ARGUMENT);

		Log.d(TAG, "Detail view received the following item: " + item);

		detailTextText.setText(item.getText());
		detailDateText.setText(item.getDateCreated().toString());
		detailUrgentCheckBox.setChecked(item.isUrgent());

		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Tell MarkAsDoneListener that this item is done
				mItemDoneListener.todoItemDone(item);
			}
		});

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
