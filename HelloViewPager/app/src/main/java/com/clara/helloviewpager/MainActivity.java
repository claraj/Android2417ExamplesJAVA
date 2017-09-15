package com.clara.helloviewpager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ViewPager viewPager = (ViewPager) findViewById(R.id.number_view_pager);
		NumberPagerAdapter pagerAdaper = new NumberPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdaper);

	}
}
