package com.gemapps.remembrall.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;

import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.detail.DetailActivity;
import com.gemapps.remembrall.ui.model.Client;
import com.gemapps.remembrall.ui.model.Delivery;
import com.gemapps.remembrall.ui.model.Job;
import com.gemapps.remembrall.ui.model.RememberAlarm;
import com.gemapps.remembrall.ui.notification.NotificationStateManager;

import java.util.List;

import io.realm.Realm;

public class AlarmNotificationReceiver extends BroadcastReceiver {

    private static final String ID_CLIENT_BUNDLE = "rememebrall.ID_CLIENT_BUNDLE";

    public static Intent createIntent(Context context, String clientId){
        Intent intent = new Intent(context, AlarmNotificationReceiver.class);
        intent.putExtra(ID_CLIENT_BUNDLE, clientId);
        return intent;
    }

    public AlarmNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String clientId = intent.getStringExtra(ID_CLIENT_BUNDLE);
        Realm realm = Realm.getDefaultInstance();
        Job job = realm.where(Job.class)
                .equalTo(RemembrallContract.JobEntry.COLUMN_ID, clientId)
                .findFirst();

        List<Delivery> mDeliveries = job.getDeliveries();
        if(mDeliveries.size() > 0){
            Delivery delivery = mDeliveries.get(0);
            final RememberAlarm alarm = delivery.getAlarm();
            alarm.setAlarmState(RememberAlarm.FIRED);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(alarm);
                }
            });
        }

        Client client = job.getClient();

        NotificationStateManager.sendNotification(context,
                "Buscar MG de "+client.getFirstName() + " " + client.getLastName(),
                createPendingIntent(context, clientId));
    }

    private PendingIntent createPendingIntent(Context context, String clientId){
        return createStack(context, clientId)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private TaskStackBuilder createStack(Context context, String clientId){
        Intent intent = DetailActivity.getInstance(context, clientId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(DetailActivity.class);
        stackBuilder.addNextIntent(intent);
        return  stackBuilder;
    }
}
