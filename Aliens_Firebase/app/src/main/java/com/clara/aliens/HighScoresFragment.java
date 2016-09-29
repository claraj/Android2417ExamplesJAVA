package com.clara.aliens;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by clara on 9/28/16.
 */

public class HighScoresFragment extends Fragment {

	private String TAG = "HIGH SCORE FRAGMENT";

	private static String SCORES = "high scores bundle key";

	private ArrayList<String> scores;

	private RestartListener listener;

	interface RestartListener {
		void playAgain();
	}

	//Receive list of names and scores
	public static HighScoresFragment newInstance(ArrayList<HighScore> highScore) {
		HighScoresFragment fragment = new HighScoresFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(SCORES, highScore);
		fragment.setArguments(bundle);
		return fragment;
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof RestartListener) {
			listener = (RestartListener) activity;
		} else {
			throw new RuntimeException("Activity must be username listener");
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_high_score, container, false);

		scores = getArguments().getStringArrayList(SCORES);

		ListView HighScores = (ListView) view.findViewById(R.id.highscore_list);

		HighScores.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, scores));

		Button playAgain = (Button) view.findViewById(R.id.play_again);

		playAgain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.playAgain();
			}
		});

		return view;

	}

}
