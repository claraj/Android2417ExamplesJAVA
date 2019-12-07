package com.clara.hydrationroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import com.clara.hydrationroom.db.HydrationViewModel;
import com.clara.hydrationroom.db.Person;
import com.clara.hydrationroom.db.Record;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HydrationViewModel viewModel = new HydrationViewModel(getApplication());

        Person alice = new Person("Alice");

        Person bob = new Person("Bob");

        viewModel.insertPerson(alice);
        viewModel.insertPerson(bob);

        viewModel.getPersonByName("Alice").observe(this, new Observer<Person>() {
            @Override
            public void onChanged(Person alice) {

                // Add some example records for Alice
                Record monday = new Record(4, "2019-12-09", alice.getId());
                Record tuesday = new Record(4, "2019-12-10", alice.getId());
                Record wednesday = new Record(2, "2019-12-11", alice.getId());

                Record[] records = { monday, tuesday, wednesday };
                viewModel.insertRecord(records);
            }
        });


        // TODO make user interface!

    }

}
