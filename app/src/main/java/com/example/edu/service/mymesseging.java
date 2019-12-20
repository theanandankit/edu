package com.example.edu.service;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.edu.R;
import com.example.edu.sunday_activity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class mymesseging extends FirebaseMessagingService {




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"mynotification")
                .setContentTitle(remoteMessage.getData().get("title"))
                .setSmallIcon(R.drawable.ic_home)
                .setContentText(remoteMessage.getData().get("message"))
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());

    }

    public void shownotification(String title,String message)
    {

        Intent intent=new Intent(getApplicationContext(), sunday_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,0);

    }
}
