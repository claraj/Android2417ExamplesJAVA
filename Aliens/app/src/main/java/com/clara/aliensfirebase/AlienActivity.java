package com.clara.aliensfirebase;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;


//TODO layout for welcome screen

public class AlienActivity extends AppCompatActivity implements WelcomeFragment.UsernameListener {

	String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alien);

		//TODO simple alien bash game

		FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.activity_alien);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		username = null; //todo fetch from firebase or device storage or whatever.

		WelcomeFragment welcomeFragment = WelcomeFragment.newInstance(username);

		ft.add(R.id.activity_alien, welcomeFragment);

		ft.commit();




	}

	@Override
	public void setUsername(String username) {
		this.username = username;

		//Start game

		GameFragment gameFragment = GameFragment.newInstance();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.activity_alien, gameFragment);
		ft.commit();

	}
}
