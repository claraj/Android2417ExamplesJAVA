package com.clara.aliens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * Created by Clara on 9/28/16.
 *
 * Either ask the user for their name, or if they have already set their name, display a welcome message.
 */

public class WelcomeFragment extends Fragment {

	private String TAG = "WELCOME FRAGMENT";

	private static String USERNAME = "username bundle key";

	private ArrayList<String> scores;

	private String mUsername;

	UsernameListener listener;

	interface UsernameListener {
		void userStartsPlay(String username);
	}

	// Provide username, if one has been set already
	public static WelcomeFragment newInstance(String username) {
		WelcomeFragment fragment = new WelcomeFragment();
		Bundle bundle = new Bundle();
		bundle.putString(USERNAME, username);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Context activity) {
		super.onAttach(activity);

		if (activity instanceof UsernameListener) {
			listener = (UsernameListener) activity;
		} else {
			throw new RuntimeException("Activity must be UsernameListener");
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
			mUsername = getArguments().getString(USERNAME);
			usernameTV.setText(getString(R.string.welcome, mUsername));
			usernameTV.setVisibility(View.VISIBLE);
		}

		else {
			usernameET.setVisibility(View.VISIBLE);
			enterNameTV.setVisibility(View.VISIBLE);
		}

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View buttonView) {

				if (mUsername != null) {
					hideKeyboard();
					listener.userStartsPlay(mUsername);
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

	//Thank you, Stack Overflow. Why is hiding the keyboard so awkward? I bet this doesn't work on all version of Android.
	// FIXME Update, nope, works on some, hanging around behavior on Oreo emulator.

	private void hideKeyboard() {

		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		Log.i(TAG, "Attempting to hiding keyboard");

	}



}
