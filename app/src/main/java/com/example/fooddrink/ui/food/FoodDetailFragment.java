package com.example.fooddrink.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddrink.Model.Food;
import com.example.fooddrink.ViewHolder.FoodViewHolder;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.FragmentFoodDetailBinding;
import com.example.fooddrink.ui.base.BaseFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoodDetailFragment extends BaseFragment<FragmentFoodDetailBinding> {
    public String Category = "";
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    public FoodDetailFragment(String category) {
        this.Category = category;
    }


    @Override
    public FragmentFoodDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentFoodDetailBinding.inflate(inflater);
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        database = PublicData.database;

    }

    @Override
    public void onClick(View view) {

    }
}
