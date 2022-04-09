package com.example.fooddrink.ui.history;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fooddrink.Model.Category;
import com.example.fooddrink.R;
import com.example.fooddrink.ViewHolder.MenuViewHolder;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.FragmentFoodMainBinding;
import com.example.fooddrink.ui.base.BaseFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryFragment extends BaseFragment<FragmentFoodMainBinding> {
    FirebaseDatabase database;
    DatabaseReference booking;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    @Override
    public FragmentFoodMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentFoodMainBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        database = PublicData.database;
        booking = database.getReference("Booking");
        binding.recyclerFood.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.textTrans.setOnClickListener(this);
        binding.textFinish.setOnClickListener(this);
        binding.textCancel.setOnClickListener(this);
    }

    private void showView(Integer show) {
        switch (show) {
            case 1:
                binding.viewTrans.setVisibility(View.VISIBLE);
                binding.viewCancel.setVisibility(View.GONE);
                binding.viewFinish.setVisibility(View.GONE);
                break;
            case 2:
                binding.viewTrans.setVisibility(View.GONE);
                binding.viewFinish.setVisibility(View.VISIBLE);
                binding.viewCancel.setVisibility(View.GONE);
                break;
            case 3:
                binding.viewCancel.setVisibility(View.VISIBLE);
                binding.viewTrans.setVisibility(View.GONE);
                binding.viewFinish.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textTrans:
                showView(1);
            case R.id.textFinish:
                showView(2);
            case R.id.textCancel:
                showView(3);
                break;
            default:
                break;
        }

    }
}
