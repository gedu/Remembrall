package com.gemapps.remembrall.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gemapps.remembrall.ui.detail.DetailActivity;
import com.gemapps.remembrall.ui.notification.NotificationStateManager;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    public AlarmNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: 1/28/17 intent will have the id so i can get the data

        NotificationStateManager.sendNotification(context,
                "Alarma para recordar buscar MG",
                createPendingIntent(context));
    }

    private PendingIntent createPendingIntent(Context context){
        return PendingIntent.getActivity(context, 0,
                new Intent(context, DetailActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
