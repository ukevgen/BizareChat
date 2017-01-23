package com.internship.pbt.bizarechat.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.view.activity.LoginActivity;

/**
 * AlertReceiver for alarm user when he opens Login screen and then moves app to background
 * or closes it pressing “Back” device button
 */

public class AlertReceiver extends BroadcastReceiver {
    public static final int notifID = 33;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, context.getString(R.string.bizare_chat),
                context.getString(R.string.forgot_to_authorize));
    }

    private void createNotification(Context context, String s, String s2) {
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, LoginActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(s)
                .setContentText(s2)
                .setAutoCancel(true);


        builder.setContentIntent(notificIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notifID, builder.build());
    }
}
