package com.clara.simpleconfirmationdialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ShowSimpleDialogActivity extends AppCompatActivity {

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

				//Dialogs are created by a Builder.
				AlertDialog.Builder builder =
						new AlertDialog.Builder(ShowSimpleDialogActivity.this);

				//Tell builder which options we'd like. Chain methods together
				builder.setMessage("I'm a dialog")
						.setTitle("This is the title")
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								userClickedOk();
							}
						})
						.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								userClickedCancel();
							}
						})
						.create()      //Create dialog
						.show();       //Show dialog
			}
		});
	}


	private void userClickedOk() {
		mDialogResult.setText("You clicked ok");
	}

	private void userClickedCancel() {
		mDialogResult.setText("You clicked cancel");
	}

}
