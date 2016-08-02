package com.gemapps.remembrall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gemapps.remembrall.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RemembrallCreationActivity extends ButterActivity {

    private static final String TAG = "RemembrallCreationActiv";

    public interface PickupDateListener {
        void onStartDatePick(long ts);
        void onEndDatePick(long ts);
    }

    public static final int REQUEST_START_DATE_RESULT = 1;
    public static final int REQUEST_END_DATE_RESULT = 2;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private PickupDateListener mPickupDateListener;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @BindView(R.id.view_pager) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rembrall_creation);

        setUpButton();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED) return;

        if(requestCode == REQUEST_START_DATE_RESULT ) {
            mPickupDateListener.onStartDatePick(data.getLongExtra(DatePickerActivity.DATA_RESULT_KEY, -1));
        }else if(requestCode == REQUEST_END_DATE_RESULT){
            mPickupDateListener.onEndDatePick(data.getLongExtra(DatePickerActivity.DATA_RESULT_KEY, -1));
        }
    }

    public void addListener(PickupDateListener listener){
        mPickupDateListener = listener;
    }

    @OnClick(R.id.fab)
    public void onFabClicked(View view){
        Snackbar.make(view, "Save", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0){
                return CreationFormFragment.newInstance();
            }else{
                return CreationAlarmFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Form";
                case 1:
                    return "Alarm";
            }
            return null;
        }
    }
}
