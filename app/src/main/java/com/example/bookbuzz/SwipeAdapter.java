package com.example.bookbuzz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SwipeAdapter extends FragmentPagerAdapter {

    public SwipeAdapter(
            FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragmentPage1();
        } else if (position == 1) {
            return new FragmentPage2();
        } else {
            return new com.example.bookbuzz.FragmentPage3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
