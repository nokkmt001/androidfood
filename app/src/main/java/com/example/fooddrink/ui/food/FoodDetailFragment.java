package com.example.fooddrink.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.fooddrink.Interface.InternClickListener;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.R;
import com.example.fooddrink.ViewHolder.FoodViewHolder;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.FragmentFoodDetailBinding;
import com.example.fooddrink.ui.base.BaseFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailFragment extends BaseFragment<FragmentFoodDetailBinding> {
    public String Category = "";
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    List<Food> listAllData = new ArrayList<>();

    public FoodDetailFragment(String category) {
        this.Category = category;
    }

    @Override
    public FragmentFoodDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentFoodDetailBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {
        database = PublicData.database ;
        foodList = database.getReference("Food");
        binding.recyclerFood.setLayoutManager(new GridLayoutManager(getContext(),2));
        if (categoryId != null) {
            loadListFood(categoryId);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId) //like: select * from Foods where MenuId =
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
//                foodViewHolder.food_name.setText(food.getName());
//                Glide.with(getContext())
//                        .load(food.getImage())
//                        .error(R.drawable.img_no_image)
//                        .override(300, 300)   // resizes the image to 300x300 pixels
//                        .into(foodViewHolder.food_image);
//                Food local = food;
//                foodViewHolder.setInternClickListener(new InternClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//
//                    }
//                });

                foodViewHolder.food_name.setText(food.getName());
                Picasso.get().load(food.getImage()).into(foodViewHolder.food_image);

                Food local = food;
                foodViewHolder.setInternClickListener(new InternClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                    }
                });
            }
        };
        //set adapter
        binding.recyclerFood.setAdapter(adapter);
    }
}
