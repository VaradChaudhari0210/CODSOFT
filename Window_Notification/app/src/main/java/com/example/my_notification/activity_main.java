package com.example.my_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;

public class activity_main extends AppCompatActivity {
    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 100;
    private Button notification_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notification_btn = findViewById(R.id.notification_btn);
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNotification();
            }
        });
        if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    public void makeNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        IconCompat iconCompat = IconCompat.createWithResource(this, R.drawable.ic_notifications);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(this)
                    .setChannelId(CHANNEL_ID)
                    .setLargeIcon(iconCompat.toIcon())
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentText("My Notification")
                    .setContentTitle("New NOTIFI")
                    .setSubText("New message from Varad")
                    .build();
            notificationManager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel",NotificationManager.IMPORTANCE_HIGH));
        }
        else{
            notification = new NotificationCompat.Builder(this)
                    .setLargeIcon(iconCompat.toIcon())
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle("New NOTIFI")
                    .setContentText("My Notification")
                    .setSubText("New message from Varad")
                    .build();
        }
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}