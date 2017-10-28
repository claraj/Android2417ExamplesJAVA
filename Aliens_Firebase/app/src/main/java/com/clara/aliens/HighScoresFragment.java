package com.clara.aliens;

import android.content.Context;
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

import java.util.LinkedList;

/**
 * Created by clara on 9/28/16.
 *
 * Shows the high score table for this game, and the user's latest score.
 *
 */

public class HighScoresFragment extends Fragment implements Firebase.HighScoreUpdateListener {

	private String TAG = "HIGH SCORE FRAGMENT";

	private static String LOCAL_USER_SCORE = "local user scores bundle key";

	private LinkedList<GameScore> scores;

	ListView highScores;

	private GameScore mLocalUserScore;

	private RestartListener listener;


	public void setScore(GameScore gameScore) {
		mLocalUserScore = gameScore;
	}


	interface RestartListener {
		void playAgain();
	}

	//Receive mScore for this user, for this round of the game
	public static HighScoresFragment newInstance(GameScore localUserScore) {
		HighScoresFragment fragment = new HighScoresFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(LOCAL_USER_SCORE, localUserScore);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}


	@Override
	public void onAttach(Context activity) {
		super.onAttach(activity);

		if (activity instanceof RestartListener) {
			listener = (RestartListener) activity;
		} else {
			throw new RuntimeException("Activity must be RestartListener");
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_high_score, container, false);
		TextView localUserScoreTV = (TextView) view.findViewById(R.id.user_score);

		GameScore localUserScore = getArguments().getParcelable(LOCAL_USER_SCORE);

		Log.d(TAG, "The local user score is " + localUserScore);

		if (localUserScore != null) {
			localUserScoreTV.setText(localUserScore.getUsername() + ", your score is " + localUserScore.getScore());
		}

		highScores = (ListView) view.findViewById(R.id.highscore_list);

		if (scores == null) {
			scores = new LinkedList<>();
			scores.add(new GameScore("High Score Table Not Available!", 0));   //dummy GameScore object to show in the empty list
		}

		highScores.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, scores));

		Button playAgain = (Button) view.findViewById(R.id.play_again);

		playAgain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.playAgain();
			}
		});

		return view;

	}


	@Override
	public void highScoresUpdated(LinkedList<GameScore> scores) {

		Log.d(TAG, "High scores data received from Firebase, about to update list " + scores);

		Log.d(TAG, "Activity: " + getActivity());
		if (getActivity() != null) {
			ArrayAdapter<GameScore> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, scores);  //fixme NPE scores update in Firebase if this Fragment has become detached from the Activity
			highScores.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	}


	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			mLocalUserScore = savedInstanceState.getParcelable(LOCAL_USER_SCORE);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		outBundle.putParcelable(LOCAL_USER_SCORE, mLocalUserScore);
	}

}
