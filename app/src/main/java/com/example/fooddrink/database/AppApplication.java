package com.example.fooddrink.database;

import android.app.Application;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new AppPreference(this);
    }
}
