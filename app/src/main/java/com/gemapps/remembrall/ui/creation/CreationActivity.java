package com.gemapps.remembrall.ui.creation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.ButterActivity;
import com.gemapps.remembrall.ui.ButterFragment;
import com.gemapps.remembrall.ui.DatePickerActivity;
import com.gemapps.remembrall.ui.widget.validationedittext.textwatcher.ValidationWatcherManager;

import butterknife.BindView;
import butterknife.OnClick;

public class CreationActivity extends ButterActivity {

    private static final String TAG = "RemembrallCreationActiv";
    private  static final String FRAGMENT_TAG = "remembrall.FRAGMENT_TAG";
    public interface PickupDateListener {
        void onStartDatePick(long ts);

        void onEndDatePick(long ts);
    }

    public static final int REQUEST_START_DATE_RESULT = 1;
    public static final int REQUEST_END_DATE_RESULT = 2;

    private PickupDateListener mPickupDateListener;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

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

    public void addListener(PickupDateListener listener) {
        mPickupDateListener = listener;
    }

    @OnClick(R.id.fab)
    public void onFabClicked(View view) {

        if(ValidationWatcherManager.getInstance().formValidationPassed()) {
            mCreationFragment.fabAction();
            finish();
        }else{
            Snackbar.make(view, "ERROR "+ValidationWatcherManager.getInstance().getFieldWithError(), Snackbar.LENGTH_LONG).show();
        }

    }

    public void makeSnackbar(String msg) {
        Snackbar.make(mCoordinatorLayout, msg, Snackbar.LENGTH_LONG).show();
    }
}
