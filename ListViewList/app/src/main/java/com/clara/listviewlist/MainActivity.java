package com.clara.listviewlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listview_arraylist);

		//Create an ArrayList and add some example data
		ArrayList<String> androidVersions = new ArrayList<String>();
		androidVersions.add("Jellybean");
		androidVersions.add("Kitkat");
		androidVersions.add("Lollypop");

		//Create Adapter: provide Context (typically this Activity), a layout file, and TextView, and List
		ArrayAdapter<String> arrayAdapter =
				new ArrayAdapter<String>(this, R.layout.list_view_item, R.id.android_version_name, androidVersions);

		listView.setAdapter(arrayAdapter);

		//Chsnges to the data - the ArrayList - are picked up by the ArrayAdapter and
		//the ListView will update
		androidVersions.add("Marshmallow");
		androidVersions.add("Nougat");

	}
}


