package com.clara.aliens;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.LinkedList;
import java.util.Random;

/**
 * Created by clara on 9/28/16.
 * Where the game happens, contains game logic, and sends the user's mScore to a EndGameListener.
 */

public class GameFragment extends Fragment {

	// Class variables; if a variable can be static and the value can be set here or in onCreate, then don't need to save in instance state.
	private static final String TAG = "GAME FRAGMENT" ;

	private static long vibrate_length = 200;
	private static final int missesPermitted = 5;
	private static final long gameSpeed = 1000;     //Time in ms between game ui updates. Larger number = slower game
	private final int scoreIncrement = 100;     // How many points per alien hit?



	private static final int[] imageViewIds = {
			R.id.alien1,
			R.id.alien2,
			R.id.alien3,
			R.id.alien4,
			R.id.alien5
	};

	private static Random rnd = new Random();

	// This gets rebuilt in onCreateView
	LinkedList<ImageView> mAliens;

	// These are instance variables and need to be saved and restored in instance state
	int mScore = 0;
	int mAliensShown = 0;
	int mAliensTapped = 0;

	// Keys for saving instance state
	final String SCORE = "mScore", ALIENS_SHOWN = "mAliensShown", ALIENS_TAPPED = "mAliensTapped";


	EndGameListener listener;

	interface EndGameListener {
		void gameEnded(int score);
	}


	public static GameFragment newInstance() {
		GameFragment fragment = new GameFragment();
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);   // Save instance state automatically (?? )
	}


	@Override
	public void onAttach(Context activity) {
		super.onAttach(activity);
		if (activity instanceof EndGameListener) {
			listener = (EndGameListener) activity;
		} else {
			throw new RuntimeException("Host Activity must be instance of EndGameListener");
		}

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		/* Create a list of Alien ImageView objects, and attach onClickListeners to each.
		* Have to wait until the onCreateView lifecycle method since this is the first opportunity to work with the view */

		View view = inflater.inflate(R.layout.fragment_game, container, false);

		mAliens = new LinkedList<>();

		for (int id : imageViewIds) {
			final ImageView alien = (ImageView) view.findViewById(id);
			alien.setImageResource(R.drawable.alien_sprite);
			mAliens.add(alien);
			alien.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					alienClicked(view);
				}
			});
		}

		return view;
	}


	private void alienClicked(View alienView) {

		// If the alienView is visible, it's the current target alier on the screen.
		// So user has hit the current alien. Increase mScore, show feedback to user, hide this alien.

		if (alienView.getVisibility() == View.VISIBLE) {

			// Increase mScore
			mAliensTapped++;
			mScore += scoreIncrement;

			// Hide this alien  (future version - show an explosion image?)
			alienView.setVisibility(View.INVISIBLE);

			Log.d(TAG, "Alien tapped " + mScore + " " + mAliensTapped + " " + mAliensShown);

			// Sound 'effect'. For a custom feedback sound, would need to upload a suitable sound file
			alienView.playSoundEffect(SoundEffectConstants.CLICK);

			Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
			if (vibrator != null) {
				vibrator.vibrate(vibrate_length);
			}
		}
	}


	final Handler gameHandler = new Handler();

	@Override
	public void onPause() {
		super.onPause();

		gameHandler.removeCallbacks(gameRunnable, null);
	}

	@Override
	public void onResume() {

		super.onResume();

		Log.d(TAG, "onResume, start game");
		//Start game
		gameHandler.postDelayed(gameRunnable, gameSpeed);

	}


	Runnable gameRunnable = new Runnable() {

		boolean show = false;

		@Override
		public void run() {

			Log.d(TAG, "RUNNABLE TICK showing or hiding (t/f): " + show +  "  mAliensShown" + mAliensShown + " mScore " + mScore + " aliens tapped " + mAliensTapped);

			//Alternately: clear screen OR display an alien at random

			if (show) {
				mAliensShown++;
				for (ImageView alien : mAliens) {
					alien.setVisibility(View.INVISIBLE);}
			} else {
				int alienIndex = rnd.nextInt(mAliens.size());
				mAliens.get(alienIndex).setVisibility(View.VISIBLE);
			}

			//run out of chances?

			if (mAliensTapped + missesPermitted < mAliensShown) {
				Log.d(TAG, "game over.");
				gameHandler.removeCallbacks(this);   // Stop handler calling itself
				listener.gameEnded(mScore);        	// Notify listener that the game ended
			}

			// Game still happening

			else {
				show = !show;          // show alien if currently hidden, hide if shown
				gameHandler.postDelayed(this, gameSpeed);   // And call the handler to repeat the run method after the given delay
			}
		}

	};



	// Save and restore instance state

	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		outBundle.putInt(SCORE, mScore);
		outBundle.putInt(ALIENS_SHOWN, mAliensShown);
		outBundle.putInt(ALIENS_TAPPED, mAliensTapped);
		Log.d(TAG, "onSaveInstanceState bundle = " + outBundle);
	}


	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);

		Log.d(TAG, "onViewStateRestored bundle = " + savedInstanceState);

		if (savedInstanceState != null) {

			mScore = savedInstanceState.getInt(SCORE, 0);
			mAliensShown = savedInstanceState.getInt(ALIENS_SHOWN, 0);
			mAliensTapped = savedInstanceState.getInt(ALIENS_TAPPED, 0);

		}
	}

}
