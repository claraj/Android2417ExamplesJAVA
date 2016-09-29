package com.clara.aliens;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by clara on 9/28/16.
 */

public class GameFragment extends Fragment {


	private static final String TAG = "GAME FRAGMENT" ;

	long gameSpeed = 500;     //Time in ms between game ui updates

	int score = 0;
	int scoreIncrement = 100;

	private long vibrate_length = 200;

	int missedPermitted = 5;

	int aliensShown = 0;
	int aliensTapped = 0;

	ArrayList<ImageView> aliens;

	int[] imageViewIds = { R.id.alien1,
			R.id.alien2,
			R.id.alien3,
			R.id.alien4,
			R.id.alien5
	};

	EndGameListener listener;


	interface EndGameListener {
		void endGame(int score);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof EndGameListener) {
			listener = (EndGameListener) activity;
		} else {
			throw new RuntimeException("Host Activity must be instance of EndGameListener");
		}

	}

	public static GameFragment newInstance() {
		GameFragment fragment = new GameFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_game, container, false);

		aliens = new ArrayList<>();

		for (int id : imageViewIds) {
			final ImageView alien = (ImageView) view.findViewById(id);
			alien.setImageResource(R.drawable.alien_sprite);
			aliens.add(alien);
			alien.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (alien.getVisibility() == View.VISIBLE) {
						alienClicked(view);
					}
				}
			});
		}

		return view;
	}

	private void alienClicked(View alienView) {

		aliensTapped++;
		score+=scoreIncrement;

		alienView.setVisibility(View.INVISIBLE);

		Log.d(TAG, "Alien tapped " + score + " " + aliensTapped + " " + aliensShown);

		//todo explosion image - not all devices can vibrate
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(vibrate_length);

	}


	@Override
	public void onResume() {
		super.onResume();

		final Random rnd = new Random();

		//Start game

		final Handler gameHandler = new Handler();

		gameHandler.postDelayed(new Runnable() {

			boolean show = false;

			@Override
			public void run() {

				//Clear screen / display a random alien on alternate ticks

				if (show) {
					aliensShown++;
					for (ImageView alien : aliens) {
						alien.setVisibility(View.INVISIBLE);}
				} else {
					int alienIndex = rnd.nextInt(aliens.size());
					aliens.get(alienIndex).setVisibility(View.VISIBLE);
				}

				//run out of chances?

				if (aliensTapped + missedPermitted < aliensShown) {
					//game over
					Log.d(TAG, "game over.");
					gameHandler.removeCallbacks(this);
						listener.endGame(score);
				}

				else {
					show = !show;
					gameHandler.postDelayed(this, gameSpeed);
				}
			}

		}, gameSpeed);

	}

}
