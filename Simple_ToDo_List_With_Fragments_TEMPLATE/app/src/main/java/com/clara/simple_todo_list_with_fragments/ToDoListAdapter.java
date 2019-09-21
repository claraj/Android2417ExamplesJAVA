package com.clara.simple_todo_list_with_fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder> {

	private static final String TAG = "TODO LIST ADAPTER";

	interface OnListItemClickListener {
		void onListItemClick(int position);
	}

	private static List<ToDoItem> mTodoItems;

	private OnListItemClickListener listener;

	public ToDoListAdapter(List<ToDoItem> items, OnListItemClickListener listener) {
		mTodoItems = items;
		this.listener = listener;
	}

	static class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		TextView text;
		TextView date;
		CheckedTextView urgent;

		private OnListItemClickListener listener;

		public ToDoViewHolder(@NonNull View itemView, OnListItemClickListener listener) {
			super(itemView);

			this.listener = listener;

			ConstraintLayout layout = (ConstraintLayout) itemView;
			text = layout.findViewById(R.id.todo_list_text_textview);
			date = layout.findViewById(R.id.todo_list_date_textview);
			urgent = layout.findViewById(R.id.todo_list_urgent_checkbox);

			itemView.setOnClickListener(this);

		}

		@Override
		public void onClick(View view) {
			listener.onListItemClick(getAdapterPosition());
		}
	}


	@NonNull
	@Override
	public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item_list_element, parent, false);
		return new ToDoViewHolder(layout, listener);
	}


	@Override
	public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
		ToDoItem item = mTodoItems.get(position);
		holder.text.setText(item.getText());
		holder.date.setText(item.getFormattedDateCreated());

		if (item.isUrgent()) {
			holder.urgent.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
		} else
			holder.urgent.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);

		Log.d(TAG, item +  " " + item.isUrgent() + " is checked " + holder.urgent.isChecked() );
	}

	@Override
	public int getItemCount() {
		return mTodoItems.size();
	}
}
