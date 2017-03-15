package com.gemapps.remembrall.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.Job;
import com.gemapps.remembrall.ui.model.RememberAlarm;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by edu on 1/27/17.
 */

public class AlarmStateManager {

    private static final String TAG = "AlarmStateManager";
    private static AlarmStateManager mInstance;

    public static AlarmStateManager getInstance(){
        if(mInstance == null) mInstance = new AlarmStateManager();
        return mInstance;
    }

    public void loadFromDbAndRegister(Context context){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Job> jobs = realm.where(Job.class).findAll();
        for (Job job : jobs) {
            registerAlarm(context, job.getId(), job.getDeliveries());
        }
    }

    public void registerAlarm(Context context, String clientId,  List<Delivery> deliveries){

        for (Delivery delivery : deliveries) {
            PendingIntent pending = createPendingIntent(context, clientId, delivery.getId());
            RememberAlarm alarm = delivery.getAlarm();
            if(alarm.getAlarmState() != RememberAlarm.FIRED) updateAlarm(context, pending, alarm);
        }
    }

    private boolean isValid(RememberAlarm alarm){
        return Calendar.getInstance().getTimeInMillis() < alarm.getEndDate();
    }

    private void updateAlarm(Context context, PendingIntent pendingIntent, RememberAlarm alarm){
        Log.d(TAG, "updateAlarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        long fireDate = alarm.getEndDate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AlarmManager.AlarmClockInfo clockInfo = new AlarmManager.AlarmClockInfo(fireDate, pendingIntent);
            alarmManager.setAlarmClock(clockInfo, pendingIntent);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, fireDate, pendingIntent);
        }
    }

    private PendingIntent createPendingIntent(Context context, String clientId, int id){
        return PendingIntent.getBroadcast(context, id,
                AlarmNotificationReceiver.createIntent(context, clientId),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
