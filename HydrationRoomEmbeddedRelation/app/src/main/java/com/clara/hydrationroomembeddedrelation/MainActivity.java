package com.clara.hydrationroomembeddedrelation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import com.clara.hydrationroomembeddedrelation.db.HydrationViewModel;
import com.clara.hydrationroomembeddedrelation.db.Person;
import com.clara.hydrationroomembeddedrelation.db.PersonWithRecords;
import com.clara.hydrationroomembeddedrelation.db.Record;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HydrationViewModel viewModel = new HydrationViewModel(getApplication());

        Person alice = new Person("Alice");
        Person bob = new Person("Bob");

        viewModel.insertPerson(alice);
        viewModel.insertPerson(bob);

        Record monday = new Record(4, "2019-12-09", 1);     // Assuming Alice has id = 1
        Record tuesday = new Record(4, "2019-12-10", 1);     // Get an actual ID for a real app
        Record wednesday = new Record(2, "2019-12-11", 1);

        Record[] records = { monday, tuesday, wednesday};
        viewModel.insertRecord(records);

        // Can still create individual Person objects, and Record objects as before.

        viewModel.getPersonWithRecordsByName("Alice").observe(this, new Observer<PersonWithRecords>() {
            @Override
            public void onChanged(PersonWithRecords personWithRecords) {
                Log.d(TAG, personWithRecords.toString());
                // Now can access Record entities, and the Person
                Person alice = personWithRecords.person;
                List<Record> aliceRecords = personWithRecords.records;
                Log.d(TAG, "Person is " + alice);
                Log.d(TAG, "Person's records are " + aliceRecords);
            }
        });

        // TODO make user interface!

    }

}
