package com.clara.travelwishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder>  {

    // Adapter's internal data store
    private List<String> data;

    // Click and long-click listener
    private WishListClickListener listener;

    // Constructor
    public WishListAdapter(List<String> data, WishListClickListener listener) {
        this.listener = listener;
        this.data = data;
    }

    // Objects of this class represent the view for one data item
    static class WishListViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {

        TextView textView;
        WishListClickListener listener;

        WishListViewHolder(TextView v, WishListClickListener listener) {
            super(v);
            this.listener = listener;
            textView = v;
            textView.setOnClickListener(this);
            textView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Notify the listener of the event, and which item was clicked
            listener.onListClick(getAdapterPosition());
        }


        @Override
        public boolean onLongClick(View view) {
            // Notify the listener of the event, and which item was long-clicked
            listener.onListLongClick(getAdapterPosition());
            return true;   // indicates event is consumed, no further processing.
            // If this is false, in this app, the click event is fired too.
        }
    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get a reference to the wish_list_element TextView and inflate in, in this context
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list_element, parent, false);
        // Create a new viewHolder, to contain this TextView
        WishListViewHolder viewHolder = new WishListViewHolder(textView, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        // Configures a ViewHolder to display the data for the given position
        // In Android terminology, bind the view and it's data
        String text = data.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
