package com.example.my_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
    private static final int REQUEST_CODE = 100;
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

        PendingIntent pendingIntent = PendingIntent.getActivity(this,REQUEST_CODE,
                new Intent(this,com.example.my_notification.Notification.class),PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine("First line")
                .addLine("Second line")
                .addLine("Third line")
                .addLine("Fourth line")
                .addLine("Fifth line")
                .addLine("Sixth line")
                .addLine("Seventh line")
                .addLine("Eighth line")
                .setBigContentTitle("Full Message")
                .setSummaryText("+2 more");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(this)
                    .setLargeIcon(iconCompat.toIcon())
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentText("My Notification")
                    .setContentTitle("New NOTIFI")
                    .setSubText("New message from Varad")
                    .setColor(Color.argb(255,128,0,0))
                    .setStyle(inboxStyle)
                    .setContentIntent(pendingIntent)
                    .setChannelId(CHANNEL_ID)
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
                    .setColor(Color.argb(255,128,0,0))
                    .setStyle(inboxStyle)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}