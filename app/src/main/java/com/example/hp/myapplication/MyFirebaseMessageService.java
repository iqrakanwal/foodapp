package com.example.hp.myapplication;

import android.app.PendingIntent;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessageService extends FirebaseMessagingService {




    public void generateNotification(String title, String messege){



        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(MyFirebaseMessageService.this, 0 ,intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);




    }

}
