package com.example.fooddrink.ui.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.databinding.ItemBookingBinding;
import com.example.fooddrink.ui.base.BaseLMAdapter;

public class BookingAdapter extends BaseLMAdapter<Food, ItemBookingBinding> {
    public Context context;
    public BookingAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void setupViews(ItemBookingBinding viewMain, Food item, int position) {
        Glide.with(context)
                .load(item.getImage())
                .override(300,300)
                .into( viewMain.imageLogo);
    }

    @Override
    public ItemBookingBinding getViewBinding(ViewGroup parent, int viewType) {
        return ItemBookingBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
