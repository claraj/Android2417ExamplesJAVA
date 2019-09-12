package com.clara.guestlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText mNewGuestNameInput;
    Button mAddGuestButton;
    TextView mGuestList;
    Button mClearGuestListButton;

    ArrayList<String> mGuests;

    private static final String GUEST_LIST_KEY = "guest-list-bundle-key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewGuestNameInput = findViewById(R.id.new_guest_input);
        mAddGuestButton = findViewById(R.id.add_guest_button);
        mGuestList = findViewById(R.id.list_of_guests);
        mClearGuestListButton = findViewById(R.id.clear_guest_list_button);

        // If there is any saved instance state variable,
        // check for data belonging to the key GUEST_LIST_KEY
        if (savedInstanceState != null) {
            mGuests = savedInstanceState.getStringArrayList(GUEST_LIST_KEY);
        }

        // If there was no saved instance state, or no data for the GUEST_LIST_KEY
        // initialize mGuests to an empty ArrayList
        if (mGuests == null) {
            mGuests = new ArrayList<>();
        }

        updateGuestList();  // display data from bundle, if present

        mAddGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGuestName = mNewGuestNameInput.getText().toString();
                if (newGuestName.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter a name", Toast.LENGTH_LONG).show();
                    return;
                }
                mGuests.add(newGuestName);
                mNewGuestNameInput.setText("");   // Clear text
                updateGuestList();
            }
        });

        mClearGuestListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuests.clear();
                updateGuestList();
            }
        });

    }

    private void updateGuestList() {
        // Display guest list in mGuestList TextView
        StringBuilder builder = new StringBuilder();
        for (String guest: mGuests) {
            builder.append(guest);
            builder.append("\n");
        }

        String list = builder.toString();
        mGuestList.setText(list);

        // Java does have a String.join() method that would be convenient here,
        // but it's not supported by Android versions before 23 (Oreo)
    }

    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        // Required to call superclass method first
        super.onSaveInstanceState(outBundle);
        outBundle.putStringArrayList(GUEST_LIST_KEY, mGuests);
    }
}




