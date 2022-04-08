package com.example.fooddrink.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddrink.Model.Food;
import com.example.fooddrink.databinding.ItemCartBinding;
import com.example.fooddrink.ui.base.BaseLMAdapter;

public class CartAdapter extends BaseLMAdapter<Food, ItemCartBinding> {

    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public CartAdapter(Context context) {
        super(context);
    }

    @Override
    public void setupViews(ItemCartBinding binding, Food item, int position) {
        binding.textTitle.setText("gfgfgfg");

        binding.imageAdd.setOnClickListener(view -> onClick.onClick(view,position));

        binding.imageMinus.setOnClickListener(view -> onClick.onClick(view,position));
    }

    @Override
    public ItemCartBinding getViewBinding(ViewGroup parent, int viewType) {
        return ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()));
    }

}
