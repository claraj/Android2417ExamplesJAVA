package com.clara.travelwishlistcustomlistview;


import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder>  {

    // Adapter's internal data store
    private List<Place> data;

    // Click and long-click listener
    private WishListClickListener listener;

    // Constructor
    public WishListAdapter(List<Place> data, WishListClickListener listener) {
        this.listener = listener;
        this.data = data;
    }

    // Objects of this class represent the view for one data item
    static class WishListViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {

        Layout layout;
        TextView nameTextView, dateCreatedTextView;

        WishListClickListener listener;

        WishListViewHolder(LinearLayout linearLayout, WishListClickListener listener) {
            super(linearLayout);
            this.listener = listener;

            nameTextView = linearLayout.findViewById(R.id.place_name_text_view);
            dateCreatedTextView = linearLayout.findViewById(R.id.date_created_text_view);

            linearLayout.setOnClickListener(this);
            linearLayout.setOnLongClickListener(this);
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
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list_element, parent, false);
        // Create a new viewHolder, to contain this TextView
        WishListViewHolder viewHolder = new WishListViewHolder(linearLayout, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        // Configures a ViewHolder to display the data for the given position
        // In Android terminology, bind the view and it's data
        Place place = data.get(position);
        holder.nameTextView.setText(place.getName());
        holder.dateCreatedTextView.setText(place.getDateCreated());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}