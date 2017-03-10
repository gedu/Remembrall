package com.gemapps.remembrall.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmBootReceiver extends BroadcastReceiver {

    public AlarmBootReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmStateManager.getInstance().loadFromDbAndRegister(context);
    }
}
