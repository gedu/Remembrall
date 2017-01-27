package com.gemapps.remembrall.ui.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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

    @IntDef(flag=true, value={ON_GOING, FIRED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AlarmStates{}
    public static final int ON_GOING = 0;
    public static final int FIRED = 1<<1;

    private String mLabel;
    private String mDescription;
    private long mStartDate;
    private long mEndDate;
    private int mAlarmState;

    public RememberAlarm() {}

    public RememberAlarm(String label, String description, long startDate,
                         long endDate) {
        this.mLabel = label;
        this.mDescription = description;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mAlarmState = ON_GOING;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getStartDate() {
        return mStartDate;
    }

    public void setStartDate(long startDate) {
        mStartDate = startDate;
    }

    public long getEndDate() {
        return mEndDate;
    }

    public void setEndDate(long endDate) {
        mEndDate = endDate;
    }

    public int getAlarmState() {
        return mAlarmState;
    }

    public void setAlarmState(@AlarmStates int alarmState) {
        mAlarmState = alarmState;
    }
}
