package com.clara.dynamicbluegreenfragments;

import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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

		updateFragment();    // Inital update

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


		//If blue fragment is shown, then replace with green fragment
		//Check if the FragmentManager is managing a Fragment with the tag BLUE_TAG
		//If not, then replace whatever is there with the BlueFragment
		if (fm.findFragmentByTag(BLUE_TAG) != null) {
			//Carry out the transaction - in this case, replace one fragment with another
			//replace() removes all Fragments from a container (like this Activity) and replaces with another Fragment
			//Add a String tag to the transaction. (Can use this to figure out what's displayed)
			//And keep track of which Fragment is currently shown
			ft.replace(android.R.id.content, greenFragment, GREEN_TAG);
		}

		//Otherwise, the green fragment is shown. Replace with blue.
		else {
			//Add a tag to this Fragment, so we are able to find out what Fragment is displayed
			ft.replace(android.R.id.content, blueFragment, BLUE_TAG);
		}

		ft.commit();  //Must commit Fragment transaction, or no changes will be made

	}
}






