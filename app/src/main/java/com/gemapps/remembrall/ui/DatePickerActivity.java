package com.gemapps.remembrall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.gemapps.remembrall.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * This activity should be start it with startActivityForResult
 */
public class DatePickerActivity extends BaseCardActivity {

    private static final String TAG = "DatePickerActivity";

    public static final String INTENT_EXTRA_DAYS_KEY = "extra_days";
    public static final String DATA_RESULT_KEY = "date_picked";

    @BindView(R.id.date_picker) DatePicker mDatePicker;

    private long mDatePicked;
    private boolean mSendResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        int daysToAdd = getIntent().getIntExtra(INTENT_EXTRA_DAYS_KEY, 0);

        setResult(RESULT_CANCELED);
        setupDatePicker(daysToAdd);

        doEntryAnimation();
        overrideTrans();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDatePicked = getTime(mDatePicker.getYear(),
                mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
    }

    private void setupDatePicker(int daysToAdd){
        Log.d(TAG, "setupDatePicker() called with: daysToAdd = [" + daysToAdd + "]");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        mDatePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),

                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.d(TAG, "onDateChanged() view = [" + view.isShown() + "], year = ["
                                + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");

                        mDatePicked = getTime(year, monthOfYear, dayOfMonth);
                    }
                });
    }

    private long getTime(int year, int month, int day){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTimeInMillis();
    }

    @OnClick(R.id.cancel_button)
    public void onCancelClicked(){
        mSendResult = false;
        dismiss(null);
    }

    @OnClick(R.id.accept_button)
    public void onAcceptClicked(){
        mSendResult = true;
        dismiss(null);
    }

    @Override
    public void finish() {

        if(mSendResult) {
            Intent data = new Intent();
            data.putExtra(DATA_RESULT_KEY, mDatePicked);
            setResult(RESULT_OK, data);
        }

        super.finish();
    }
}
