package com.gemapps.remembrall.ui.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.gemapps.remembrall.R;

/**
 * Created by edu on 1/28/17.
 */

public class NotificationStateManager {

    private static final int NOTIFICATION_ID = 0;

    public static void sendNotification(Context context, String message, PendingIntent pendingIntent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(R.drawable.ic_warning_white_24px);
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText(message);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}
