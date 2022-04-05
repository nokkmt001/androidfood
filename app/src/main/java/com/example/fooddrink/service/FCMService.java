package com.example.fooddrink.service;

import static com.example.fooddrink.database.AppApplication.Chanel_id;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.fooddrink.MainActivity;
import com.example.fooddrink.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.List;

public class FCMService extends FirebaseMessagingService {

    public FCMService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Chanel_id);
        RemoteMessage.Notification ii = remoteMessage.getNotification();
        Log.e("NOTIFICATION",new Gson().toJson(ii));
        if (ii==null){
            return;
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle() == null ? "" : remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody() == null ? "" : remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(pi);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Chanel_id, "FCM_CHANNEL_ID", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

        if (!isAppIsInBackground(this)){
//            Intent intentG = new Intent();
//            intentG.setAction(TestConstants.ACTION_MAIN_PENDING);
//            intentG.putExtra("change", "change");
//            sendBroadcast(intentG);
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("From Token", "Refreshed token: " + token);
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        }

        return isInBackground;
    }
}
