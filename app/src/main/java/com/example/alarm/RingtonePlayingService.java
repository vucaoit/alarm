package com.example.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class RingtonePlayingService extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("RingService", "onBlind running");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("RingService","Running...");
        String state = intent.getExtras().getString("extra");
        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent snoozeIntent = new Intent(this, AlarmReceiver.class);
        snoozeIntent.putExtra("extra", "stop");
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("WAKE UP" + "!")
                .setContentText("Alarm is running")
                .setSmallIcon(R.mipmap.icon_alarm)
                .addAction(R.mipmap.icon_cancel, "TURN OFF",
                        snoozePendingIntent)
                .setAutoCancel(true)
                .build();

        System.out.println(state);
        if(!state.equals("stop")){
            mediaPlayer = MediaPlayer.create(this,R.raw.ringsound);
            mediaPlayer.start();
            mNM.notify(0,mNotify);
        }
        if(state.equals("stop")){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        return START_NOT_STICKY;
    }

}