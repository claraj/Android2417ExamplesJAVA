package com.clara.hellosleep;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv = (TextView) findViewById(R.id.tv);

		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				tv.setText("There will be a toast in 3 seconds...");

				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						done();
					}
				}, 3000);

			}
		});
	}

	private void done() {
		tv.setText("Tap here to show a toast in 3 seconds");
		Toast.makeText(this, "3 seconds are up", Toast.LENGTH_LONG).show();
	}
}
