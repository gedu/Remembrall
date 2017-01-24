package com.gemapps.remembrall.ui.model;

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

    private String mLabel;
    private String mDescription;
    private long mStartDate;
    private long mEndDate;
    private int mAlarmType;

    public RememberAlarm() {}

    public RememberAlarm(String label, String description, long startDate,
                         long endDate, int alarmType) {
        this.mLabel = label;
        this.mDescription = description;
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mAlarmType = alarmType;
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

    public int getAlarmType() {
        return mAlarmType;
    }

    public void setAlarmType(int alarmType) {
        mAlarmType = alarmType;
    }
}
