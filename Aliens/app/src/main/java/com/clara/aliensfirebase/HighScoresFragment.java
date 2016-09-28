package com.clara.aliensfirebase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by clara on 9/28/16.
 */

public class HighScoresFragment extends Fragment {

	private String TAG = "HIGH SCORE FRAGMENT";

	private static String SCORES = "high scores bundle key";

	private ArrayList<String> scores;

	//Receive list of names and scores
	public static HighScoresFragment newInstance(ArrayList<String> highScore) {
		HighScoresFragment fragment = new HighScoresFragment();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(SCORES, highScore);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_high_score, container, false);

		scores = getArguments().getStringArrayList(SCORES);

		TextView tempHighScores = (TextView) view.findViewById(R.id.highscore_temp);

		//TODO put scores into high score table or list or whatever.

		tempHighScores.setText(scores.toString()); ///hacky hack, replace.

		return view;

	}

}
