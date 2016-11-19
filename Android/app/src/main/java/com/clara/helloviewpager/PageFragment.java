package com.clara.helloviewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_NUMBER = "param_number";

	// TODO: Rename and change types of parameters
	private int mParam;


	public PageFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param number Parameter 1.
	 * @return A new instance of fragment BlankFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PageFragment newInstance(int number) {
		PageFragment fragment = new PageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_NUMBER, number);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam = getArguments().getInt(ARG_NUMBER);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_page, container, false);
		TextView number = (TextView) view.findViewById(R.id.number_tv);
		number.setText("The number of this page is " + mParam);
		return view;
	}

}
