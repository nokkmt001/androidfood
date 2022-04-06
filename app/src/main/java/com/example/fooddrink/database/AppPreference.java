package com.example.fooddrink.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    public static final String PREF_USER_ID = "user_login_id";
    public static final String PREF_USER_PASS = "user_login_pass";
    public static final String PREF_IS_LOGIN = "is_login";


    private static SharedPreferences sharedPreferences;

    public AppPreference(Context mContext) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }
    public static boolean isLogin() {
        return sharedPreferences.getBoolean(PREF_IS_LOGIN, false);
    }

    public static void setLogin(boolean status) {
        sharedPreferences.edit().putBoolean(PREF_IS_LOGIN, status).apply();
    }

    public static String getUser() {
        return sharedPreferences.getString(PREF_USER_ID, null);
    }

    public static void setUser(String gg) {
        sharedPreferences.edit().putString(PREF_USER_ID, gg).apply();
    }

    public static String getUserPass() {
        return sharedPreferences.getString(PREF_USER_PASS, null);
    }

    public static void setUserPass(String gg) {
        sharedPreferences.edit().putString(PREF_USER_PASS, gg).apply();
    }
}
