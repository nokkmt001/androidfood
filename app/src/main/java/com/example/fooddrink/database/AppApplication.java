package com.example.fooddrink.database;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class AppApplication extends Application {
    public static final String Chanel_id = "FCM_CHANNEL_ID";

    @Override
    public void onCreate() {
        super.onCreate();
        new AppPreference(this);
        PublicData.database = FirebaseDatabase.getInstance();
    }
}
