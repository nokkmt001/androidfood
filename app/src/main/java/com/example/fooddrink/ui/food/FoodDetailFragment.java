package com.example.fooddrink.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddrink.databinding.FragmentFoodDetailBinding;
import com.example.fooddrink.ui.base.BaseFragment;

public class FoodDetailFragment extends BaseFragment<FragmentFoodDetailBinding> {
    public String Category = "";

    @Override
    public FragmentFoodDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentFoodDetailBinding.inflate(inflater);
    }

    public FoodDetailFragment(String category) {
        this.Category = category;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
