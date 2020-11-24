package com.example.daraltebassistant.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.daraltebassistant.fragment.AfternoonFragment;
import com.example.daraltebassistant.fragment.EveningFragment;
import com.example.daraltebassistant.fragment.MorningFragment;

public class TimeSlotViewPager extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    public TimeSlotViewPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return MorningFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return AfternoonFragment.newInstance();
            case 2: // Fragment # 1 - This will show SecondFragment
                return EveningFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "MORNING";
            case 1:
                return "AFTERNOON";
            case 2:
                return "EVENING";
            default:
                return null;
        }
    }
}
