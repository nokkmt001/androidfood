package com.example.fooddrink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivityFoodDetailBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.cart.CartActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends BaseTestActivity<ActivityFoodDetailBinding> {
    Food food;
    FirebaseDatabase database;
    DatabaseReference foodData;
    String key = "";
    List<Food> listData = new ArrayList<>();
    Integer count = 1;

    @Override
    public ActivityFoodDetailBinding getViewBinding() {
        return ActivityFoodDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.imageAdd.setOnClickListener(view12 -> {
            count = count + 1;
            binding.textCountBuy.setText(count.toString());
        });
        binding.imageMinus.setOnClickListener(view13 -> {
            if (count != 1) {
                count = count - 1;
            }
            binding.textCountBuy.setText(count.toString());
        });
        binding.btnAddCart.setOnClickListener(view -> {
            if (food == null) return;
            if (key == null) return;
            food.setFoodID(key);
            food.setCount(count);

            if (PublicData.listDataFoodBooking == null) {
                PublicData.listDataFoodBooking = new ArrayList<>();
                PublicData.listDataFoodBooking.add(food);
            } else {
                for (Food item : PublicData.listDataFoodBooking) {
                    if (food.getFoodID().equals(item.getFoodID())) {
                        item.setCount(count);
                    } else {
                        PublicData.listDataFoodBooking.add(food);
                    }
                }
            }
            showToast("Thêm vào giỏ hàng thành công");
        });
        binding.btnBuyNow.setOnClickListener(view -> {
            if (food == null) return;
            if (key == null) return;
            food.setFoodID(key);
            PublicData.listDataFoodBookingNow.add(food);
            Intent intent = new Intent(this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.layoutHeader.imageCart.setOnClickListener(view -> {
            Intent intent = new Intent(this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        binding.layoutHeader.imageDrawer.setOnClickListener(view -> finish());
    }

    @Override
    protected void initData() {
        database = PublicData.database;

        Bundle bundle = getIntent().getExtras();
        food = (Food) bundle.getSerializable("ITEM");
        key = bundle.getString("ITEMID");
        if (food != null) {
            Glide.with(this)
                    .load(food.getImage())
                    .error(R.drawable.img_no_image)
                    .into(binding.imageMain);
            binding.textDescription.setText(food.getDescription());
            binding.textDiscount.setText(food.getDiscount());
            binding.textPrice.setText(food.getPrice());
            binding.textName.setText(food.getName());
        }
    }

    @Override
    public void onClick(View view) {

    }
}