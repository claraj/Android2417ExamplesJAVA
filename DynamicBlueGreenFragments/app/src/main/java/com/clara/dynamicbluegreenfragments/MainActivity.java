package com.clara.dynamicbluegreenfragments;

import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	//Keep track of the id of the current fragment displayed. Arbitrarily, start with blue.
	private int fragmentDisplayed = R.id.blue_fragment;

	//Create instances of our two Fragments
	private GreenFragment greenFragment = new GreenFragment();
	private BlueFragment blueFragment = new BlueFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		updateFragment();

		//add click listener to main window
		//android.R.id.content is a built-in reference to your Activity's main UI component
		
		View v = findViewById(android.R.id.content);
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateFragment();
			}
		});
	}

	private void updateFragment() {

		//Get a reference to this Activity's FragmentManager
		FragmentManager fm = getFragmentManager();
		//Request the FragmentMananger begins a Fragment Transaction
		FragmentTransaction ft = fm.beginTransaction();

		//If blue Fragment is shown, then replace with green fragment
		if (fragmentDisplayed == R.id.blue_fragment) {
			//Carry out the transaction - in this case, replace one fragment with another
			//replace() removes all Fragments from a container and replaces with another Fragment
			ft.replace(android.R.id.content, greenFragment);
			//And keep track of which Fragment is currently shown
			fragmentDisplayed = R.id.green_fragment;
		}

		//Otherwise, the green fragment is shown. Replace with blue.
		else {
			ft.replace(android.R.id.content, blueFragment);
			fragmentDisplayed = R.id.blue_fragment;
		}

		ft.commit();  //Must commit Fragment transaction, or no changes will be made

	}
}



