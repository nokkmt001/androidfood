package com.example.fooddrink.utils;

import java.text.SimpleDateFormat;

public class AppUtils {

    public static String formatDateToString(java.util.Date date, String formatOut) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatOut);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }
}
