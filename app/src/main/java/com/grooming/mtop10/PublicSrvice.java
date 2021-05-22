package com.grooming.mtop10;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.widget.Toast;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PublicSrvice extends Service {

    private static final String SERVICE_CHANNEL = "public service channel";
    private static final String INTENT_PARAMETER = "parameter";


    Binder binder = new IGetDataFromDB.Stub() {
        @Override
        public List<String> getData(String col) throws RemoteException {
            Context context = PublicSrvice.this;
            List<String> list = new ArrayList<>();
            list.add("one");
            list.add("two");
            list.add("three");
            list.add("service package:" + context.getPackageName());
            int uid = getCallingUid();
            String foreign = getPackageManager().getPackagesForUid(uid)[0];
            list.add("caller package:" + foreign);
            return list;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    protected void onHandleIntent(@Nullable Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            Context context = getApplicationContext();
            String title = context.getString(R.string.app_name);
            NotificationChannel default_channel =
                    new NotificationChannel(SERVICE_CHANNEL, "service notif channel",
                            NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(default_channel);
            Notification notification =
                    new Notification.Builder(context, SERVICE_CHANNEL)
                            .setContentTitle(title)
                            .setSmallIcon(android.R.drawable.btn_default)
                            .setContentText("notification from service")
                            .setAutoCancel(true)
                            .setWhen(System.currentTimeMillis())
                            .build();
            startForeground(1, notification);
        }

        String param = intent.getStringExtra(INTENT_PARAMETER);
        Toast.makeText(this,
                String.format("Recieved parameter \"%s\"", param),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "public service destroyed", Toast.LENGTH_SHORT).show();
    }
}