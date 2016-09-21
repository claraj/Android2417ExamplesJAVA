
package com.clara.dynamicbluegreenfragments;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements BlueFragment.RandomNumberGeneratedListener {

	public static final String RANDOM_BUNDLE_KEY = "Your random number";

	//Create instances of our two Fragments
	private GreenFragment greenFragment = new GreenFragment();
	private BlueFragment blueFragment = new BlueFragment();

	//Used to label the current Fragment displayed
	private static final String BLUE_TAG = "BLUE";
	private static final String GREEN_TAG = "GREEN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		ft.add(R.id.green_fragment_container, greenFragment);
		ft.add(R.id.blue_fragment_container, blueFragment);

		ft.commit();

		//showBlueFragment();   //Need to show something to start with
	}

//	private void showBlueFragment() {
//		FragmentManager fm = getFragmentManager();
//		FragmentTransaction ft = fm.beginTransaction();
//		ft.add(android.R.id.content, blueFragment, BLUE_TAG);
//		ft.commit();
//	}

	// Send the random number to the GreenFragment
	public void sendRandomNumber(int rnd) {

		greenFragment.setRandom(rnd);

	}
}

