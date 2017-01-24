package com.gemapps.remembrall.ui.widget;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.gemapps.remembrall.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by edu on 1/24/17.
 */

public class DatePickerHandler {

    private static final String TAG = "DatePickerHandler";

    @BindView(R.id.date_picker)
    DatePicker mDatePicker;

    private long mDatePicked;

    public DatePickerHandler(View rootView, int daysToAdd, long timestamp){
        ButterKnife.bind(this, rootView);
        setupDatePicker(daysToAdd, timestamp);
    }

    private void setupDatePicker(int daysToAdd, long ts){
        Log.d(TAG, "setupDatePicker() called with: daysToAdd = [" + daysToAdd + "]");

        Calendar calendar = Calendar.getInstance();
        if(ts != -1) calendar.setTimeInMillis(ts);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        mDatePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),

                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        setDate(year, monthOfYear, dayOfMonth);
                    }
                });

        setDate(mDatePicker.getYear(),
                mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
    }

    private void setDate(int year, int month, int day){

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        mDatePicked = calendar.getTimeInMillis();
    }

    public long getPickedDate(){
        return mDatePicked;
    }
}
