package com.simplestepapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.simplestepapp.R;
import com.simplestepapp.fragments.BrushingFragment;
import com.simplestepapp.fragments.ColonCleanFragment;
import com.simplestepapp.fragments.MentalFitFragment;
import com.simplestepapp.fragments.PhysicalFitFragment;
import com.simplestepapp.fragments.SunBothFragment;
import com.simplestepapp.fragments.WakeUpFragment;
import com.simplestepapp.fragments.WaterInTakeFragment;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener {

    private static final int NUMBER_OF_PAGES = 7;
    @SuppressLint("StaticFieldLeak")
    public static ViewPager pager;
    public static ArrayList<String> timeSlots=new ArrayList<>();

    public static int dis_Position=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        toolbarsetUp();

        timeSlots = new ArrayList<>();
        timeSlots.add("5:00");
        timeSlots.add("5:15");
        timeSlots.add("5:30");
        timeSlots.add("5:45");
        timeSlots.add("6:00");
        timeSlots.add("6:15");
        timeSlots.add("6:30");
        timeSlots.add("6:45");
        timeSlots.add("7:00");
        timeSlots.add("7:15");
        timeSlots.add("7:30");
        timeSlots.add("7:45");

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.beginFakeDrag();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(this);
    }

    public void toolbarsetUp() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.lyt_header, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        /**
         * Constructor
         * @param fm
         */
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment based on the position.
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return WakeUpFragment.newInstance("FirstFragment, Instance 1");
                case 1:
                    return BrushingFragment.newInstance("SecondFragment, Instance2");
                case 2:
                    return ColonCleanFragment.newInstance("ThirdFragment, Instance3");
                case 3:
                    return WaterInTakeFragment.newInstance("FirstFragment, Instance 1");
                case 4:
                    return PhysicalFitFragment.newInstance("SecondFragment, Instance2");
                case 5:
                    return MentalFitFragment.newInstance("ThirdFragment, Instance3");
                case 6:
                    return SunBothFragment.newInstance("ThirdFragment, Instance3");
                default:
                    return WakeUpFragment.newInstance("FirstFragment, Default");
            }
        }

        /**
         * Return the number of pages.
         * @return
         */
        @Override
        public int getCount() {
            return NUMBER_OF_PAGES;
        }


    }

}
