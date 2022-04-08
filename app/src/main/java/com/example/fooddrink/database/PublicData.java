package com.example.fooddrink.database;

import com.example.fooddrink.Model.Food;
import com.example.fooddrink.Model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PublicData {
    public static FirebaseDatabase database = null;

    public static List<Food> listDataFoodBooking = null;

    public static List<Food> listDataFoodBookingNow = null;

    public static User currentUser;

    public static void clear(){
        database = null;
        listDataFoodBooking = null;
        listDataFoodBookingNow = null;
    }

}
