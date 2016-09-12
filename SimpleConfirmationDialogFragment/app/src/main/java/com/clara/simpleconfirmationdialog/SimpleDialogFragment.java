package com.clara.simpleconfirmationdialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class SimpleDialogFragment extends DialogFragment {

	interface SimpleDialogFragmentListener {
		void userClickedOk();
		void userClickedCancel();
	}

	SimpleDialogFragmentListener mDialogListener;

	private final static String MESSAGE_ARG = "Dialog message";

	public static SimpleDialogFragment newInstance(String message) {
		SimpleDialogFragment fragment = new SimpleDialogFragment();
		Bundle args = new Bundle();
		args.putString(MESSAGE_ARG, message);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//Set the Activity as the listener
		if (activity instanceof  SimpleDialogFragmentListener) {
			mDialogListener = (SimpleDialogFragmentListener) activity;
		} else {
			throw new RuntimeException(activity.toString() +
					" must implement SimpleDialogFragmentListener");
		}
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		String message = getArguments().getString(MESSAGE_ARG);
		if (message == null) {
			//Set a default message
			message = "Please click Ok or Cancel";
		}

		AlertDialog dialog = builder.setTitle("OK-Cancel Dialog Fragment")
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mDialogListener.userClickedOk();
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mDialogListener.userClickedCancel();
					}
				})
				.create();

		return dialog;
	}
}


