package com.shahid.aietest.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.shahid.aietest.R;
import com.shahid.aietest.ui.tasks.TasksActivity;
import com.shahid.aietest.utills.NetworkConnectivityUtill;


public class ConnectivityService extends Service {

    private static final String TAG=ConnectivityService.class.getName();
    static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CONNECTIVITY_CHANGE_ACTION);

        registerReceiver(receiver,filter);
        return START_NOT_STICKY;
    }



    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (CONNECTIVITY_CHANGE_ACTION.equals(action)) {
                //check internet connection
                if (!NetworkConnectivityUtill.isConnectedOrConnecting(context)) {
                    if (context != null) {
                        boolean show = false;
                        if (NetworkConnectivityUtill.lastNoConnectionTs == -1) {//first time
                            show = true;
                            NetworkConnectivityUtill.lastNoConnectionTs = System.currentTimeMillis();
                        } else {
                            if (System.currentTimeMillis() - NetworkConnectivityUtill.lastNoConnectionTs > 1000) {
                                show = true;
                                NetworkConnectivityUtill.lastNoConnectionTs = System.currentTimeMillis();
                            }
                        }

                        if (show && NetworkConnectivityUtill.isOnline) {
                            NetworkConnectivityUtill.isOnline = false;
                            showNotification(getString(R.string.connectivity_tag),getString(R.string.no_network_availability));
                        }
                    }
                } else {
                    showNotification(getString(R.string.connectivity_tag),getString(R.string.network_availability));
                    NetworkConnectivityUtill.isOnline = true;
                }
            }
        }
    };


    // notification builder pre/post API 26
    private void showNotification(String title,String message)
    {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Intent intent = new Intent(this, TasksActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(title)
                .setLargeIcon(icon)
                .setColor(getResources().getColor(R.color.purple_200))
                .setLights(getResources().getColor(R.color.purple_200), 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(getResources().getColor(R.color.teal_200));
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onDestroy() {
        if(receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}