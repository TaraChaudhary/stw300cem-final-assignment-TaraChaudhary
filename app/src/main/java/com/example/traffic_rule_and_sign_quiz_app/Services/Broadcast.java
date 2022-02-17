package com.example.traffic_rule_and_sign_quiz_app.Services;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.traffic_rule_and_sign_quiz_app.R;

public class Broadcast extends BroadcastReceiver {
    NotificationManagerCompat notificationManagerCompat;
    Context context;

    public Broadcast(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean noCon;
        notificationManagerCompat = NotificationManagerCompat.from(context);
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            noCon = intent.getBooleanExtra(ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false);
            if (noCon){
                displayNotification("Disconnected");
//                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show();
            }
            else {
                displayNotification("Connected");
//                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void displayNotification(String msg){
        Notification notification = new NotificationCompat
                .Builder(context, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Wifi")
                .setContentText(msg)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(1,notification);
    }
}
