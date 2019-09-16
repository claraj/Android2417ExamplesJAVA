package com.clara.travelwishlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WishListClickListener {

    private RecyclerView mWishListRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button mAddButton;
    private EditText mNewPlaceNameEditText;

    private List<String> mPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlaces = new ArrayList<>();

        mPlaces.add("Tokyo");
        mPlaces.add("Budapest");
        mPlaces.add("Machu Picchu");

        mWishListRecyclerView = findViewById(R.id.wish_list);
        mAddButton = findViewById(R.id.add_place_button);
        mNewPlaceNameEditText = findViewById(R.id.new_place_name);

        // Configure RecyclerView
        mWishListRecyclerView.setHasFixedSize(true);                    // If the view will not be resized, not the number of items in the list
                                                                        // Makes it more efficient to generate the ViewHolders
        mLayoutManager = new LinearLayoutManager(this);                 // ViewHolders will be displayed in a column
        mWishListRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WishListAdapter(mPlaces, this);                        // Create the Adapter to provide ViewHolders for data
        mWishListRecyclerView.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPlace = mNewPlaceNameEditText.getText().toString();
                if (newPlace.isEmpty()) {
                    return;
                }

                mPlaces.add(newPlace);
                mAdapter.notifyItemInserted(mPlaces.size() - 1);  // The last element
                mNewPlaceNameEditText.getText().clear();
            }
        });

    }

    @Override
    public void onListClick(int position) {
        String place = mPlaces.get(position);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(place));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri );
        startActivity(mapIntent);
    }

    @Override
    public void onListLongClick(int position) {
        final int itemPosition = position;

        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_place_message, mPlaces.get(position) ))
                .setTitle(getString(R.string.delete_dialog_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPlaces.remove(itemPosition);
                        mAdapter.notifyItemRemoved(itemPosition);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)    // No event handler needed for Cancel button
                .create();

        confirmDeleteDialog.show();
    }

}