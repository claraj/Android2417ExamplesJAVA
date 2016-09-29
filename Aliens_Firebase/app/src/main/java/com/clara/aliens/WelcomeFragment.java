package com.clara.aliens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 9/28/16.
 */

public class WelcomeFragment extends Fragment {

	private String TAG = "WELCOME FRAGMENT";

	private static String USERNAME = "username bundle key";

	private ArrayList<String> scores;

	private String username;

	UsernameListener listener;

	interface UsernameListener {
		void userStartsPlay(String username);
	}

	//Receive list of names and scores
	public static WelcomeFragment newInstance(String username) {
		WelcomeFragment fragment = new WelcomeFragment();
		Bundle bundle = new Bundle();
		bundle.putString(USERNAME, username);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof UsernameListener) {
			listener = (UsernameListener) activity;
		} else {
			throw new RuntimeException("Activity must be username listener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_welcome, container, false);

		TextView usernameTV = (TextView) view.findViewById(R.id.welcome_username);
		final TextView enterNameTV = (TextView) view.findViewById(R.id.enter_username_instructions);
		final EditText usernameET = (EditText) view.findViewById(R.id.player_name);
		Button playButton = (Button) view.findViewById(R.id.play_button);

		// If a username set, display username and play button
		// Otherwise, show EditText for username entry, and play button

		if (getArguments() != null && getArguments().getString(USERNAME) != null) {
			username = getArguments().getString(USERNAME);
			usernameTV.setText("Welcome, " + username);
			usernameTV.setVisibility(View.VISIBLE);
		}

		else {
			usernameET.setVisibility(View.VISIBLE);
			enterNameTV.setVisibility(View.VISIBLE);
		}

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View buttonview) {

				if (username != null) {
					hideKeyboard();
					listener.userStartsPlay(username);
				}

				else {

					String newUsername = usernameET.getText().toString();
					if (newUsername.length() == 0) {
						Toast.makeText(getActivity(), "Enter your name", Toast.LENGTH_LONG).show();
					} else {
						hideKeyboard();
						listener.userStartsPlay(newUsername);
					}
				}
			}
		});

		return view;

	}

	//Stack overflow. Why is hiding the keyboard so awkward?
	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		Log.i(TAG, "Hiding keyboard");

	}

}
