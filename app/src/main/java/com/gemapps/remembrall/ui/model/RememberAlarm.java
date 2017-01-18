package com.gemapps.remembrall.ui.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;

/**
 * Created by edu on 8/10/16.
 */

public class RememberAlarm extends RealmObject {

    private static final String TAG = "RememberAlarm";
    //TODO: this will be better to be in the res string
    public static final String DEFAULT_ALARM_LABEL = "Recordatorio";
    public static final String DEFAULT_ALARM_DESCRIPTION = "Avisar al cliente que se acerca " +
            "la fecha de devolucion del equipo";
    public static final int DEFAULT_ALARM_TYPE = 0;

    public static final String ALARM_LABEL = "mLabel";
    public static final String ALARM_DESCRIPTION = "mDescription";
    public static final String ALARM_START_DATE = "start_date";
    public static final String ALARM_END_DATE = "end_date";
    public static final String ALARM_TYPE = "type";


    public String mLabel;
    public String mDescription;
    public long mStartDate;
    public long mEndDate;
    public int mAlarmType;

    public RememberAlarm() {}

    public RememberAlarm(String label, String description, long startDate,
                         long endDate, int alarmType) {
        this.mLabel = label;
        this.mDescription = description;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mAlarmType = alarmType;
    }

    public JSONObject convertTo() {
        JSONObject obj = new JSONObject();

        try {
            obj.put(ALARM_LABEL, mLabel);
            obj.put(ALARM_DESCRIPTION, mDescription);
            obj.put(ALARM_START_DATE, mStartDate);
            obj.put(ALARM_END_DATE, mEndDate);
            obj.put(ALARM_TYPE, mAlarmType);
        } catch (JSONException e) {
            Log.e(TAG, "JSON error for " + mLabel);
        }

        return obj;
    }
}
