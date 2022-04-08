package com.example.fooddrink.ui.cart;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.fooddrink.Model.Food;
import com.example.fooddrink.databinding.ItemCartBinding;
import com.example.fooddrink.ui.base.BaseLMAdapter;

public class CartAdapter extends BaseLMAdapter<Food, ItemCartBinding> {
    @Override
    public void setupViews(ItemCartBinding binding, Food item, int position) {
        binding.textTitle.setText("gfgfgfg");
    }

    @Override
    public ItemCartBinding getViewBinding(ViewGroup parent, int viewType) {
        return ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()));
    }

}
