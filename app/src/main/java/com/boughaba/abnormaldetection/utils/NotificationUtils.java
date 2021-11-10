package com.boughaba.abnormaldetection.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationUtils {
    private static final String CHANNEL_ID = "health_guard_channel";
    private static final String CHANNEL_NAME = "Health Guard";
    private static final String CHANNEL_DESCRIPTION = "This notification shows when the health guard service is running";

    private static NotificationManager notificationManager;

    private static void getInstance(Context context){
        if(notificationManager == null){
            synchronized (NotificationUtils.class){
                if(notificationManager == null){
                    notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        setupChannels();
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public static Notification getForegroundNotification(Context context) {
        getInstance(context);
        NotificationCompat.Builder notificationBuilder = new
                NotificationCompat.Builder(context, CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Health Guard")
                .setContentText("Health Guard Service is running")
                .setPriority(NotificationManager.IMPORTANCE_LOW)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        return notification;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private static void setupChannels() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
        channel.setDescription(CHANNEL_DESCRIPTION);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
}
