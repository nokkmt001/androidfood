package com.example.fooddrink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.ViewHolder.FoodViewHolder;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivityFoodListBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.cart.CartActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodListDetailActivity extends BaseTestActivity<ActivityFoodListBinding> {
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "", categoryName = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    public ActivityFoodListBinding getViewBinding() {
        return ActivityFoodListBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        database = PublicData.database ;
        foodList = database.getReference("Food");
        binding.recyclerFood.setLayoutManager(new GridLayoutManager(this, 2));
        //get intent here
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
            categoryName = getIntent().getStringExtra("CategoryName");
            System.out.println(categoryId + ".............................................");
        }
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFood(categoryId);
            System.out.println(categoryId + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        binding.layoutHeader.imageCart.setOnClickListener(view -> {
            Intent intent = new Intent(FoodListDetailActivity.this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        binding.layoutHeader.imageDrawer.setOnClickListener(view -> finish());
        binding.layoutHeader.textTitle.setText(categoryName);
    }

    private void loadListFood(String categoryId) {
        System.out.println(">.......................");
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId) //like: select * from Foods where MenuId =
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                foodViewHolder.food_name.setText(food.getName());
//                Picasso.get().load(food.getImage()).into(foodViewHolder.food_image);
                Glide.with(FoodListDetailActivity.this)
                        .load(food.getImage())
                        .error(R.drawable.img_no_image)
                        .into(foodViewHolder.food_image);
                foodViewHolder.setInternClickListener((view, position, isLongCick) -> {
                            Toast.makeText(FoodListDetailActivity.this, "" + food.getName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FoodListDetailActivity.this, FoodDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ITEM", food);
                            bundle.putString("ITEMID", adapter.getRef(position).getKey());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                );
            }
        };
        //set adapter
        binding.recyclerFood.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }
}
