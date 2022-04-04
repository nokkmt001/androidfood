package com.example.fooddrink.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddrink.databinding.ActivityFoodListBinding;

public class TestFragment extends BaseFragment<ActivityFoodListBinding>{
    @Override
    public ActivityFoodListBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return ActivityFoodListBinding.inflate(inflater);
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
