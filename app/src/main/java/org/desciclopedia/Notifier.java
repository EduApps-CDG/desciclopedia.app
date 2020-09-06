package org.desciclopedia;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import android.content.Context;

public class Notifier {
    private RemoteViews notificationLayout;
    private RemoteViews notificationLayoutS;
    private Notification customNotification;
    private NotificationChannel mChannel;
    private NotificationManager notificationManager;
    private boolean important;
    private String CHANNEL_ID = Global.NOTIFY;
    private String CHANNEL_NAME = Global.NOTIFY_NAME;
    private int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;

    public Notifier(Activity act, boolean important) {
        this.important = important;

        if (important) {
            CHANNEL_ID = Global.I_NOTIFY;
            CHANNEL_NAME = Global.I_NOTIFY_NAME;
        }

        notificationLayout = new RemoteViews(act.getPackageName(), R.layout.notification);
        notificationLayoutS = new RemoteViews(act.getPackageName(), R.layout.notification_small);
        mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
        notificationManager = (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);
        customNotification = new NotificationCompat.Builder(act,Global.NOTIFY)
                .setSmallIcon(R.drawable.icon)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayoutS)
                .setCustomBigContentView(notificationLayout)
                .build();

        mChannel.enableLights(Global.ENABLE_LED);
        mChannel.enableVibration(Global.ENABLE_VIBRATOR);
        mChannel.setVibrationPattern(Global.NOTIFICATION_PATTERN);
        mChannel.setDescription(Global.NOTIFY_DESCRIPT);
        mChannel.setLightColor(Global.NOTIFY_LIGHT);

        customNotification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
    }

    public void show() {
        notificationManager.createNotificationChannel(mChannel);
        notificationManager.notify(0, customNotification);
    }
}
