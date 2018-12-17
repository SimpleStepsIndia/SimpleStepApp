package com.simplestepapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.simplestepapp.R;
import com.simplestepapp.fragments.BrushingFragment;
import com.simplestepapp.fragments.ColonCleanFragment;
import com.simplestepapp.fragments.WakeUpFragment;

/**
 * Created by Srinivas on 12/17/2018.
 */

public class ViewPagerActivity extends FragmentActivity
        implements ViewPager.OnPageChangeListener {

    private static final int NUMBER_OF_PAGES = 7;
    @SuppressLint("StaticFieldLeak")
    public static ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        pager = (ViewPager) findViewById(R.id.viewPager);
        //pager.beginFakeDrag();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(this);
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
                    return BrushingFragment.newInstance("SecondFragment, Instance 1");
                case 2:
                    return ColonCleanFragment.newInstance("ThirdFragment, Instance 1");
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
