package org.desciclopedia;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.content.Context;

/**
 * Notificador
 *
 * @TODO: adaptar para androids anteriores a Oreo (8.0)
 */
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

    /**
     * Prepara uma notificação para ser chamada
     *
     * @param act a atividade que vai chamar a notificação
     * @param important se a notificação é importante, true. se não for, false.
     */
    public Notifier(Activity act, boolean important) {
        this.important = important;

        //caso a notificação for importante, seta as variaveis
        if (important) {
            CHANNEL_ID = Global.I_NOTIFY;
            CHANNEL_NAME = Global.I_NOTIFY_NAME;
        }

        //layout de notificação extendida
        notificationLayout = new RemoteViews(act.getPackageName(), R.layout.notification);
        //layout de notificação minimizada
        notificationLayoutS = new RemoteViews(act.getPackageName(), R.layout.notification_small);

        //configura o canal de notificação (apenas no android Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);

            mChannel.enableLights(Global.ENABLE_LED); //suporte a LED
            mChannel.enableVibration(Global.ENABLE_VIBRATOR); //suporte a Vibração customizada
            mChannel.setVibrationPattern(Global.NOTIFICATION_PATTERN); //seta a vibração
            mChannel.setDescription(Global.NOTIFY_DESCRIPT); //descrição
            mChannel.setLightColor(Global.NOTIFY_LIGHT); //troca a cor do LED
        }
        notificationManager = (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);
        customNotification = new NotificationCompat.Builder(act,Global.NOTIFY)
                .setSmallIcon(R.drawable.icon)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayoutS)
                .setCustomBigContentView(notificationLayout)
                .build();

        customNotification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
    }

    /**
     * Mostra a notificação
     */
    public void show() {
        //ativa o canal de notificação caso o dispositivo tenha Oreo+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(mChannel);
        }

        //notificar
        notificationManager.notify(0, customNotification);
    }
}
