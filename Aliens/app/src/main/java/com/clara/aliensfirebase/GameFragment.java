package com.clara.aliensfirebase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by admin on 9/28/16.
 */

public class GameFragment extends Fragment {


	public static GameFragment newInstance() {
		GameFragment fragment = new GameFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_game, container, false);


		return view;

	}
}
