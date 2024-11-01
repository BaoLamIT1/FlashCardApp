package com.example.flashcat.Receiver;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.flashcat.R;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "notifyRemind";
    private static final String CHANNEL_NAME = "Reminders Channel";
    private static final String CHANNEL_DESCRIPTION = "Channel for reminder notifications";
    private String deskname;


    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);
        if(intent.getAction() != null){
        String action = intent.getAction();
        Log.d("haha", "onItemSelected: ");
        if(action.equals("com.example.NOTIFY")){
            deskname = intent.getStringExtra("deskName");}
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.user)
                .setContentTitle("Don't Forget to Study at Your Desk!")
                .setContentText(deskname)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
