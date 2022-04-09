package com.example.fooddrink.ui.booking;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fooddrink.Model.Booking;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivityBookingBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class BookingActivity extends BaseTestActivity<ActivityBookingBinding> {
    BookingAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference foodBooking;

    @Override
    public ActivityBookingBinding getViewBinding() {
        return ActivityBookingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        adapter = new BookingAdapter(this);
        binding.rclMain.setLayoutManager(new LinearLayoutManager(this));
        binding.rclMain.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        database = PublicData.database;
        foodBooking = database.getReference("Booking");

        String ff = foodBooking.push().getKey();
        adapter.clearData();
        adapter.addData(AppPreference.getListFoodBooking());
        String time = Calendar.getInstance().getTime().toString();
//        Booking booking = new Booking(time)
    }

    @Override
    public void onClick(View view) {

    }
}