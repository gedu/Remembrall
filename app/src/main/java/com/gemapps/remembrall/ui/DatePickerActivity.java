package com.gemapps.remembrall.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.ui.widget.DatePickerHandler;

import butterknife.OnClick;

/**
 * This activity should be start it with startActivityForResult
 */
public class DatePickerActivity extends BaseCardActivity {

    private static final String TAG = "DatePickerActivity";

    public static final String INTENT_EXTRA_DAYS_KEY = "extra_days";
    public static final String INTENT_EXTRA_SET_TS= "set_ts";

    public static final String DATA_RESULT_KEY = "date_picked";

    private DatePickerHandler mDatePickerHandler;
    private boolean mSendResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        int daysToAdd = getIntent().getIntExtra(INTENT_EXTRA_DAYS_KEY, 0);
        long ts = getIntent().getLongExtra(INTENT_EXTRA_SET_TS, -1);
        mDatePickerHandler = new DatePickerHandler(findViewById(R.id.view_content), daysToAdd, ts);
        setResult(RESULT_CANCELED);

        doEntryAnimation();
        overrideTrans();
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
            long pickedDate = mDatePickerHandler.getPickedDate();
            Intent data = new Intent();
            data.putExtra(DATA_RESULT_KEY, pickedDate);
            setResult(RESULT_OK, data);
        }

        super.finish();
    }
}
