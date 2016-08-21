package com.clara.lmgtfy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Get references to widgets - the Button, EditText and show_search_text TextViww
		Button webSearchButton = (Button) findViewById(R.id.google_search_button);
		// Any widgets you refer to from within an event handler must be declared final
		final EditText webSearchBox = (EditText) findViewById(R.id.google_search_box);
		final TextView confirmSearchText = (TextView) findViewById(R.id.show_search_text);

		webSearchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//Read the text in the EditText box
				String searchString = webSearchBox.getText().toString();
				//Display that to the user to notify that the search is about to begin...
				confirmSearchText.setText("Searching for " + searchString);

				//Display confirmation Toast
				Toast.makeText(MainActivity.this, "Searching for " + searchString, Toast.LENGTH_LONG).show();

				//Create Intent to launch web browser

				//First, create search URI
				String uriText = "https://www.google.com/search?q=" + searchString;
				Uri searchUri = Uri.parse(uriText);

				//Create Intent with this URI and the web browse action
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, searchUri);
				//Send Intent to Android syatem to start web browser Activity
				startActivity(browserIntent);
			}
		});
	}
}

