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

	//To save a reference to something that can receive a random number, so can call methods to send data
	RandomNumberGeneratedListener randomListener;

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		View view = inflater.inflate(R.layout.blue_fragment, container, false);

		Button sendRndToGreen = (Button) view.findViewById(R.id.send_rnd_to_green_button);
		sendRndToGreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Generate a random number and send it to the RandomNumberGeneratedListener.
				//This Fragment doesn't know or care what happens to the random number.
				Random rng = new Random();
				int rnd = rng.nextInt(100);   //Random number between 0 and 99
				randomListener.sendRandomNumber(rnd);
			}
		});

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		//Typically, the listener is the hosting Activity. It doesn't have to by, but usually is.
		if ( getActivity() instanceof RandomNumberGeneratedListener) {
			randomListener = (RandomNumberGeneratedListener) getActivity();
		} else {
			throw new RuntimeException(getActivity().getClass().toString() + " should implement RandomNumberGeneratedListener");
		}
	}


	interface RandomNumberGeneratedListener {
		void sendRandomNumber(int rnd);
	}

}





