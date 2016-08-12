package com.gemapps.remembrall.ui.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by edu on 8/10/16.
 */

public class RememberAlarm {

    private static final String TAG = "RememberAlarm";
    //TODO: this will be better to be in the res string
    public static final String DEFAULT_ALARM_LABEL = "Recordatorio";
    public static final String DEFAULT_ALARM_DESCRIPTION = "Avisar al cliente que se acerca " +
            "la fecha de devolucion del equipo";
    public static final int DEFAULT_ALARM_TYPE = 0;

    public static final String ALARM_LABEL = "label";
    public static final String ALARM_DESCRIPTION = "description";
    public static final String ALARM_START_DATE = "start_date";
    public static final String ALARM_END_DATE = "end_date";
    public static final String ALARM_TYPE = "type";


    public String label;
    public String description;
    public long startDate;
    public long endDate;
    public int alarmType;

    public RememberAlarm(String label, String description, long startDate,
                           long endDate, int alarmType) {
        this.label = label;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.alarmType = alarmType;
    }

    public JSONObject convertTo() {
        JSONObject obj = new JSONObject();

        try {
            obj.put(ALARM_LABEL, label);
            obj.put(ALARM_DESCRIPTION, description);
            obj.put(ALARM_START_DATE, startDate);
            obj.put(ALARM_END_DATE, endDate);
            obj.put(ALARM_TYPE, alarmType);
        } catch (JSONException e) {
            Log.e(TAG, "JSON error for " + label);
        }

        return obj;
    }
}
