package com.doubleclick.notificationservice;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "" + context, Toast.LENGTH_SHORT).show();
        Log.e("CONTEXT", "" + context);
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);
    }
}
