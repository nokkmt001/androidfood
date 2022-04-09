package com.example.fooddrink.ui.booking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fooddrink.Model.Booking;
import com.example.fooddrink.Model.User;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.databinding.ActivityBookingBinding;
import com.example.fooddrink.ui.address.ChangeAddressActivity;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.utils.AppUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends BaseTestActivity<ActivityBookingBinding> {
    BookingAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference foodBooking;
    List<Food> listAllData = new ArrayList<>();
    public static Integer CHANGEADDRESS = 1;
    User info;
    String numberPhone = "";
    Booking booking;

    @Override
    public ActivityBookingBinding getViewBinding() {
        return ActivityBookingBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        info = AppPreference.getUserMain();
        adapter = new BookingAdapter(this);
        numberPhone = AppPreference.getUserPhone();

        database = PublicData.database;
        foodBooking = database.getReference("Booking");

        String ff = foodBooking.push().getKey();

        binding.rclMain.setLayoutManager(new LinearLayoutManager(this));
        binding.rclMain.setAdapter(adapter);
        binding.textAddress.setText(info.getName() + " \n" + numberPhone + "\n" +
                info.getAddress());
        binding.textAddress.setOnClickListener(v -> {
            Intent intent = new Intent(BookingActivity.this, ChangeAddressActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, CHANGEADDRESS);
        });
        binding.buttonOk.setOnClickListener(view -> {
            assert ff != null;
            foodBooking.child(ff).setValue(booking);
        });
    }

    @Override
    protected void initData() {
        String time = AppUtils.formatDateToString(Calendar.getInstance().getTime(), "yyyy-MM-dd'T'HH:mm:ss");
        booking = new Booking(time, "01", info.getAddress(), "01", 0, numberPhone, 10000, info.getName());
        listAllData = AppPreference.getListFoodBooking();
        if (listAllData != null) {
            adapter.clearData();
            adapter.addData(listAllData);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        // activity result
        if (resultCode == RESULT_OK) {
            if (requestCode == CHANGEADDRESS) {
                initView();
                initData();
            }
        }
    }
}