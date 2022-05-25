package com.sem.smartmailbox;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.database.annotations.Nullable;

import androidx.core.app.NotificationCompat;

import static com.sem.smartmailbox.App.CHANNEL_1_ID;
import static com.sem.smartmailbox.App.CHANNEL_2_ID;

public class YourService extends Service {
    private static final int NOTIF_ID = 2;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        startForeground();



        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                CHANNEL_2_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_done)
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }



}
