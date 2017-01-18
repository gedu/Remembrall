package com.gemapps.remembrall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.widget.FloatingActionHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class RemembrallCreationActivity extends ButterActivity {

    private static final String TAG = "RemembrallCreationActiv";
    private  static final String FRAGMENT_TAG = "remembrall.FRAGMENT_TAG";
    public interface PickupDateListener {
        void onStartDatePick(long ts);

        void onEndDatePick(long ts);
    }

    public interface OnButtonClicked {
        void onFabCliecked();
    }
    public static final int REQUEST_START_DATE_RESULT = 1;
    public static final int REQUEST_END_DATE_RESULT = 2;

    private PickupDateListener mPickupDateListener;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    public OnButtonClicked mButtonListener;

    private ButterFragment mCreationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rembrall_creation);

        setUpButton();
        setupFragmentChecking(savedInstanceState);
    }

    private void setupFragmentChecking(Bundle savedInstanceState){

        if(savedInstanceState == null){
            mCreationFragment = CreationFormFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, mCreationFragment, FRAGMENT_TAG)
                    .commit();
        }else{
            mCreationFragment = (ButterFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) return;

        if (requestCode == REQUEST_START_DATE_RESULT) {
            mPickupDateListener.onStartDatePick(data.getLongExtra(DatePickerActivity.DATA_RESULT_KEY, -1));
        } else if (requestCode == REQUEST_END_DATE_RESULT) {
            mPickupDateListener.onEndDatePick(data.getLongExtra(DatePickerActivity.DATA_RESULT_KEY, -1));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FloatingActionHelper.getInstance().animateToValid(mFab);
    }

    public void addListener(PickupDateListener listener) {
        mPickupDateListener = listener;
    }

    @OnClick(R.id.fab)
    public void onFabClicked(View view) {
        if(mButtonListener != null) {
            mButtonListener.onFabCliecked();
            Snackbar.make(view, "Save", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void makeSnackbar(String msg) {
        Snackbar.make(mCoordinatorLayout, msg, Snackbar.LENGTH_LONG).show();
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

            if (position == 0) {
                return CreationFormFragment.newInstance();
            } else {
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
