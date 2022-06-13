package com.doubleclick.notificationservice;

import static com.doubleclick.notificationservice.App.CHANNEL_ID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void showNotification(View v) {
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Create bubble intent
        Intent target = new Intent(this, MainActivity.class);
        PendingIntent bubbleIntent =
                PendingIntent.getActivity(this, 0, target, 0 /* flags */);

        String CATEGORY_TEXT_SHARE_TARGET =
                "com.example.category.IMG_SHARE_TARGET";

        Person chatPartner = new Person.Builder()
                .setName("Chat partner")
                .setImportant(true)
                .build();

// Create sharing shortcut
        String shortcutId = "Genrat";
        ShortcutInfo shortcut = new ShortcutInfo.Builder(this, shortcutId)
                .setCategories(Collections.singleton(CATEGORY_TEXT_SHARE_TARGET))
                .setIntent(new Intent(Intent.ACTION_DEFAULT))
                .setLongLived(true)
                .setShortLabel(chatPartner.getName())
                .build();

// Create bubble metadata
        Notification.BubbleMetadata bubbleData =
                new Notification.BubbleMetadata.Builder(bubbleIntent,
                        Icon.createWithResource(this, R.drawable.ic_launcher_background))
                        .setDesiredHeight(600)
                        .build();

// Create notification, referencing the sharing shortcut
        Notification.Builder builder =
                new Notification.Builder(this, CHANNEL_ID)
                        .setContentIntent(bubbleIntent)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setBubbleMetadata(bubbleData)
                        .setShortcutId(shortcutId)
                        .addPerson(chatPartner);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded);

        Intent clickIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,
                0, clickIntent, 0);

        collapsedView.setTextViewText(R.id.text_view_collapsed_1, "Hello World!");

        expandedView.setImageViewResource(R.id.image_view_expanded, R.drawable.ic_launcher_background);
        expandedView.setOnClickPendingIntent(R.id.image_view_expanded, clickPendingIntent);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();

        notificationManager.notify(1, /*notification */ builder.build());
    }


}