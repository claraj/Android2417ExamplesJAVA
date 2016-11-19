package com.clara.helloviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by admin on 11/19/16.
 */

public class NumberPagerAdapter extends FragmentPagerAdapter {

	public NumberPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return PageFragment.newInstance(position);
	}

	@Override
	public int getCount() {
		return 5;
	}
}
