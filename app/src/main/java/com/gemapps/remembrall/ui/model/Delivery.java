package com.gemapps.remembrall.ui.model;

import io.realm.RealmObject;

/**
 * Created by edu on 1/23/17.
 */

public class Delivery extends RealmObject {

    private RememberAlarm mAlarm;
    private float mPrice;
    private int mId;

    public Delivery() {}

    public Delivery(RememberAlarm alarm, float price) {
        mAlarm = alarm;
        mPrice = price;
        mId = (int) System.currentTimeMillis();
    }

    public RememberAlarm getAlarm() {
        return mAlarm;
    }

    public void setAlarm(RememberAlarm alarm) {
        mAlarm = alarm;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }
}
