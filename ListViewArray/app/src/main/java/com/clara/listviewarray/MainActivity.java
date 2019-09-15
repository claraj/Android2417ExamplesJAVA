package com.clara.listviewarray;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	//An array to provide data for the list
	private final String[] mAndroidVersions = {"Jellybean", "Kitkat", "Lollipop",
			"Marshmallow", "Oreo", "Nougat", "Pie"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Get a reference to the ListView
		 ListView listView = (ListView) findViewById(R.id.simple_list);

		//Create an ArrayAdapter, note generic type <String> used
		//Arguments are Context, the TextView's resource ID, a List or array of the generic type
		ArrayAdapter<String> arrayAdapter =
				new ArrayAdapter<>(this, R.layout.list_item, R.id.list_item_text, mAndroidVersions);

		//And set this ArrayAdapter to be the the ListView's adapter
		listView.setAdapter(arrayAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// The positions in the list correspond to array indexes
				String itemText = mAndroidVersions[position];
				Toast.makeText(MainActivity.this, itemText, Toast.LENGTH_SHORT).show();
			}
		});
	}
}



