package com.clara.dialoglistsetbackgroundcolor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ColorActivity extends AppCompatActivity
		implements ColorPickerDialogFragment.ColorDialogSelectionListener {

	Button mChooseBackgroundColor;
	RelativeLayout mBackground;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color);

		mBackground = (RelativeLayout) findViewById(R.id.background);
		mChooseBackgroundColor = (Button) findViewById(R.id.show_color_dialog_button);

		mChooseBackgroundColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Show dialog
				ColorPickerDialogFragment dialog = ColorPickerDialogFragment.newInstance();
				dialog.show(getFragmentManager(), "Color Dialog");
			}
		});
	}

	@Override
	public void colorSelected(int color) {
		mBackground.setBackgroundColor(color);
	}
}
