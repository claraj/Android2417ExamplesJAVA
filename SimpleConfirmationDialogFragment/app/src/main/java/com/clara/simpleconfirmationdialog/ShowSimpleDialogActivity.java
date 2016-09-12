package com.clara.simpleconfirmationdialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowSimpleDialogActivity extends AppCompatActivity
		implements SimpleDialogFragment.SimpleDialogFragmentListener{

	private TextView mDialogResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_simple_dialog);

		mDialogResult = (TextView) findViewById(R.id.dialog_result_text);

		Button showDialog = (Button) findViewById(R.id.show_dialog_box);
		showDialog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SimpleDialogFragment dialog = SimpleDialogFragment.newInstance();
				dialog.show(getFragmentManager(), "Simple Dialog");
			}
		});
	}


	 public void userClickedOk() {
		mDialogResult.setText("You clicked ok");
	}

	public void userClickedCancel() {
		mDialogResult.setText("You clicked cancel");
	}

}
