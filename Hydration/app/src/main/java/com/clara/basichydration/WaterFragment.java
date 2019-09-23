package com.clara.basichydration;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Displays the day of the week, the number of glasses of water drunk
 * Has buttons for adding a glass for a day, and reset the count to zero
 * Updates the WaterViewModel as the user edits the number of glasses
 *
 * Uses livedata to keep the number of glasses up to date
 *
 * Use the {@link WaterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterFragment extends Fragment {

    private static final String TAG = "WATER_FRAGMENT";

    private String day;

    private WaterViewModel waterViewModel;
    private WaterRecord waterRecord;

    private static final int MAX_GLASSES = 5;

    private TextView dayText;
    private RatingBar glassesDrunk;
    private ImageButton addButton;
    private ImageButton resetButton;

    private static final String ARG_DAY_STRING = "arg_day_string";

    public WaterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of WaterFragment
     * Provide a day (e.g. Monday) which should be a day in the database.
     */

    public static WaterFragment newInstance(String day) {
        WaterFragment fragment = new WaterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DAY_STRING, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getString(ARG_DAY_STRING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_water, container, false);

        dayText = view.findViewById(R.id.day_of_week);
        glassesDrunk = view.findViewById(R.id.water_rating_bar);
        addButton = view.findViewById(R.id.add_glass_button);
        resetButton = view.findViewById(R.id.reset_count_button);

        dayText.setText(day);

        waterViewModel = ViewModelProviders.of(getActivity()).get(WaterViewModel.class);

        waterViewModel.getRecordForDay(day).observe(this, new Observer<WaterRecord>() {
            @Override
            public void onChanged(WaterRecord record) {
                Log.d(TAG, "onCreate, found record: " + record);
                if (record != null) {
                    waterRecord = record;
                    glassesDrunk.setRating(record.getGlasses());
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (waterRecord == null) { return; }

                int glasses = waterRecord.getGlasses();
                if (glasses < MAX_GLASSES) {
                    waterRecord.setGlasses(++glasses);   // prefix increment, increase before variable is passed to method
                    waterViewModel.update(waterRecord);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (waterRecord == null) { return; }

                waterRecord.setGlasses(0);
                waterViewModel.update(waterRecord);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



    }

}
