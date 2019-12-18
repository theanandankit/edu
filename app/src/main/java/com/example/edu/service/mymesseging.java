package com.example.edu.service;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.edu.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class mymesseging extends FirebaseMessagingService {




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        shownotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));

    }

    public void shownotification(String title,String message)
    {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"mynotification")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.college_logo)
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
}
