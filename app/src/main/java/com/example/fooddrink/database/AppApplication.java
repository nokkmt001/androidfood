package com.example.fooddrink.database;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.hawk.Hawk;

public class AppApplication extends Application {
    public static final String Chanel_id = "FCM_CHANNEL_ID";

    @Override
    public void onCreate() {
        super.onCreate();
        new AppPreference(this);
        PublicData.database = FirebaseDatabase.getInstance("https://fooddrink-4797d-default-rtdb.asia-southeast1.firebasedatabase.app");
        Hawk.init(this).build();
    }
}
