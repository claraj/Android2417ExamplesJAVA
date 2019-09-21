package com.clara.simple_todo_list_with_fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * Fragment to show detail about one To Do item. Has 'Done' button to mark item as done.
 */
public class ToDoItemDetailFragment extends Fragment {

	interface MarkItemAsDoneListener {
		void todoItemDone(ToDoItem doneItem);
	}

	private static final String ARG_TODO_ITEM = "todo item argument";
	private static final String TAG = "TODO ITEM DETAIL FRAG";

	private MarkItemAsDoneListener mItemDoneListener;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);

		if (context instanceof MarkItemAsDoneListener) {
			mItemDoneListener = (MarkItemAsDoneListener) context;
		} else {
			throw new RuntimeException(context.toString() + " must implement MarkItemAsDoneListener");
		}
	}

	//Use in place of a constructor. Set arguments for this Fragment.
	// onCreateView will be able to get this data.
	public static ToDoItemDetailFragment newInstance(ToDoItem item) {
		final Bundle args = new Bundle();
		args.putParcelable(ARG_TODO_ITEM, item);
		final ToDoItemDetailFragment fragment = new ToDoItemDetailFragment();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_to_do_item_detail, container, false);

		//Get the To Do item from the arguments passed in when this Fragment was created.
		final ToDoItem item = getArguments().getParcelable(ARG_TODO_ITEM);
		Log.d(TAG, "onCreateView received the following item: " + item);

		//Set up the view
		final TextView detailTextText = view.findViewById(R.id.to_do_detail_text_textview);
		final TextView detailDateText =  view.findViewById(R.id.to_do_detail_date_created_textview);
		final CheckedTextView detailUrgentCheckBox = view.findViewById(R.id.to_do_detail_urgent_checkbox);
		Button doneButton =  view.findViewById(R.id.to_do_detail_done_button);

		detailTextText.setText(item.getText());
		detailDateText.setText(item.getFormattedDateCreated());

		if (item.isUrgent()) {
			detailUrgentCheckBox.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
		} else {
			detailUrgentCheckBox.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
		}

		//Event handler
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Clear the data about this to do item
				detailTextText.setText("");
				detailDateText.setText("");
				detailUrgentCheckBox.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
				//Tell MarkAsDoneListener that this item is done
				mItemDoneListener.todoItemDone(item);
			}
		});

		return view;
	}




}
