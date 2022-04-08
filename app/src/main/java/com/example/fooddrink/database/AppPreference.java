package com.example.fooddrink.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.fooddrink.Model.Food;
import com.example.fooddrink.Model.User;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class AppPreference {
    public static final String PREF_USER_ID = "user_login_id";
    public static final String PREF_USER_PASS = "user_login_pass";

    public static final String PREF_USER_MAIN = "user_login_main";

    public static final String PREF_IS_LOGIN = "is_login";
    public static final String PREF_LIST_FOOD_BOOK = "food_book";

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

    public static String getUserPhone() {
        return sharedPreferences.getString(PREF_USER_ID, null);
    }

    public static void setUserPhone(String gg) {
        sharedPreferences.edit().putString(PREF_USER_ID, gg).apply();
    }

    public static String getUserPass() {
        return sharedPreferences.getString(PREF_USER_PASS, null);
    }

    public static void setUserPass(String gg) {
        sharedPreferences.edit().putString(PREF_USER_PASS, gg).apply();
    }

    /**
     * Save
     **/

    public static void saveListFoodBooking(List<Food> info) {
        if (info == null) {
            clearListFoodBooking();
        } else {
            Hawk.put(PREF_LIST_FOOD_BOOK, info);
        }
    }

    public static void saveUserMain(User user) {
        if (user == null) {
            clearUserMain();
        } else {
            Hawk.put(PREF_USER_MAIN, user);
        }
    }

    /**
     * Get
     **/

    public static List<Food> getListFoodBooking() {
        return Hawk.get(PREF_LIST_FOOD_BOOK);
    }

    public static User getUserMain() {
        return Hawk.get(PREF_USER_MAIN);
    }

    /**
     * Clear
     **/

    public static void clearListFoodBooking() {
        Hawk.delete(PREF_LIST_FOOD_BOOK);
    }

    public static void clearUserMain() {
        Hawk.delete(PREF_USER_MAIN);
    }

}
