package com.gemapps.remembrall.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.gemapps.remembrall.ui.detail.DetailActivity;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.RememberAlarm;

import java.util.List;

/**
 * Created by edu on 1/27/17.
 */

public class AlarmStateManager {

    private static AlarmStateManager mInstance;

    public static AlarmStateManager getInstance(){
        if(mInstance == null) mInstance = new AlarmStateManager();

        return mInstance;
    }

    public void registerAlarm(Context context, List<Delivery> deliveries){

        for (Delivery delivery : deliveries) {
            updateAlarm(context, delivery.getAlarm());
        }
    }

    private void updateAlarm(Context context, RememberAlarm alarm){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long fireDate = alarm.getEndDate();
        PendingIntent pendingIntent = createPendingIntent(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo clockInfo = new AlarmManager.AlarmClockInfo(fireDate, pendingIntent);
            alarmManager.setAlarmClock(clockInfo, pendingIntent);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, fireDate, pendingIntent);
        }
    }

    private PendingIntent createPendingIntent(Context context){
        return PendingIntent.getBroadcast(context, 0,
                new Intent(context, AlarmNotificationReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
