package com.clara.simpleconfirmationdialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ShowSimpleDialogActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_simple_dialog);

		Button showDialog = (Button) findViewById(R.id.show_dialog_box);
		showDialog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Dialogs are created by a Builder.
				// There's so many possible options when creating a Dialog, so constructors are really awkward.
				// 'Builder' is the name of a design pattern that tries to address this - Google for more info.
				// Dialogs belong to an Activity, so provide a reference to this Activity.
				AlertDialog.Builder builder =
						new AlertDialog.Builder(ShowSimpleDialogActivity.this);

				// Tell builder which options we'd like. Chain methods together
				builder.setMessage("I'm a dialog")
						.setTitle("This is the title")
						.setPositiveButton(android.R.string.ok, null);

				// Request the builder creates a Dialog with the specified options
				AlertDialog dialog = builder.create();

				// And show the dialog
				dialog.show();

			}
		});
	}
}



