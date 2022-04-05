package com.example.fooddrink.database;

import com.google.firebase.database.FirebaseDatabase;

public class PublicData {
    public static FirebaseDatabase database = null;

    public static void clear(){
        database = null;
    }
}
