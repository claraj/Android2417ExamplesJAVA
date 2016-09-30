package com.clara.aliens;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by clara on 9/28/16.
 */

public class HighScoresFragment extends Fragment implements Firebase.HighScoreUpdateListener {

	private String TAG = "HIGH SCORE FRAGMENT";

	private static String LOCAL_USER_SCORE = "high scores bundle key";

	private ArrayList<HighScore> scores;

	ListView highScores;

	private RestartListener listener;

	@Override
	public void highScoresUpdated(ArrayList<HighScore> scores) {

		Log.d(TAG, "High scores data received from firebase, about to update list " + scores);
		ArrayAdapter<HighScore> adapter = new ArrayAdapter<HighScore>(getActivity(), R.layout.list_item, scores);
		highScores.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	interface RestartListener {
		void playAgain();
	}

	//Receive score for this user, for this round of the game
	public static HighScoresFragment newInstance(HighScore localUserScore) {
		HighScoresFragment fragment = new HighScoresFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(LOCAL_USER_SCORE, localUserScore);
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

		HighScore localUserScore = getArguments().getParcelable(LOCAL_USER_SCORE);

		TextView localUserScoreTV = (TextView) view.findViewById(R.id.user_score);

		localUserScoreTV.setText(localUserScore.getUsername() + ", your score is " + localUserScore.getScore());

		highScores = (ListView) view.findViewById(R.id.highscore_list);

		if (scores == null) {
			scores = new ArrayList<>();
			scores.add(new HighScore("High Score Table Not Available!", 0));   //dummy score. Bet you can think of a nicer way to do this!
		}

		highScores.setAdapter(new ArrayAdapter<HighScore>(getActivity(), R.layout.list_item, scores));

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
