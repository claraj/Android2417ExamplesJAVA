package com.clara.basichydration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MAIN_ACTIVITY";

    private WaterViewModel waterViewModel;

    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create ViewModel, associate with this Activity
        // The fragment will also be able to access it
        waterViewModel = new WaterViewModel(getApplication());

        // For debugging only - not used in this Activity
        waterViewModel.getAllRecords().observe(this, new Observer<List<WaterRecord>>() {
            @Override
            public void onChanged(List<WaterRecord> waterRecords) {
                Log.d(TAG, "Water records are: " + waterRecords);
            }
        });

        // Insert blank days, so there is a record in the database for each day of the week.
        // DAO has conflict strategy = ignore for duplicate days, so if any of the
        // days are already in the database, they will be ignored, not set to zero glasses
        for (String day: DAYS) {
            WaterRecord record = new WaterRecord(day, 0);
            Log.d(TAG, "Inserting" + record);
            waterViewModel.insert(record);
        }

        // Create view pager using the DAYS array to create a page for each day in the DAYS array
        ViewPager viewPager = findViewById(R.id.water_view_pager);
        WaterViewPagerAdapter waterViewPagerAdapter = new WaterViewPagerAdapter(getSupportFragmentManager(), DAYS);
        viewPager.setAdapter(waterViewPagerAdapter);

        /*
        // Original version with one day - create and add the fragment directly to the Activity
        WaterFragment waterFragment = WaterFragment.newInstance("Monday");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content, waterFragment);
        ft.commit();
        */

    }
}
