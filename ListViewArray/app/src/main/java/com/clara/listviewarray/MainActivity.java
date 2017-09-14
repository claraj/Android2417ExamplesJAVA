package com.clara.listviewarray;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Get a reference to the ListView
		 ListView listView = (ListView) findViewById(R.id.simple_list);

		//An array to provide some data for the list
		String[] androidVersions = {"Ice cream sandwich", "Jellybean", "Kitkat", "Lollipop"};

		//Create an ArrayAdapter. Note generic type <String> used
		//Arguments are Context, a TextView's resource ID, a List of the generic type
		ArrayAdapter<String> arrayAdapter =
				new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item_text, androidVersions);

		//And set this ArrayAdapter to be the the ListView's adapter
		listView.setAdapter(arrayAdapter);

	}
}


