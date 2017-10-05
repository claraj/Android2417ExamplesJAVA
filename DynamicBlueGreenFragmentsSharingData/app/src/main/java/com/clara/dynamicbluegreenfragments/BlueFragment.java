package com.clara.dynamicbluegreenfragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

/* For generating a random number */

public class BlueFragment extends Fragment {

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		View view = inflater.inflate(R.layout.blue_fragment, container, false);

		Button sendRndToGreen = (Button) view.findViewById(R.id.send_rnd_to_green_button);
		sendRndToGreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Generate a random number and send it to the Activity.
				//The Activity will then send it to the Green Fragment
				//Fragments should be independent -
				//This Fragment shouldn't know or care what happens to the random number
				Random rng = new Random();
				int rnd = rng.nextInt(100);   //Random number between 0 and 99

				MainActivity hostingActivity = (MainActivity) getActivity();
				hostingActivity.sendRandomNumber(rnd);
			}
		});

		return view;
	}


}





