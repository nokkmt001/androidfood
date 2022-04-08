package com.example.fooddrink.ui.booking;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fooddrink.databinding.ActivityBookingBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;

public class BookingActivity extends BaseTestActivity<ActivityBookingBinding> {
    BookingAdapter adapter;

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

    }

    @Override
    public void onClick(View view) {

    }
}