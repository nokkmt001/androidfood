package com.example.fooddrink.ui.food;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.fooddrink.FoodListDetailActivity;
import com.example.fooddrink.Model.Category;
import com.example.fooddrink.R;
import com.example.fooddrink.ViewHolder.MenuViewHolder;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.FragmentFoodMainBinding;
import com.example.fooddrink.ui.base.BaseFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodMainFragment extends BaseFragment<FragmentFoodMainBinding> {
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";
    DatabaseReference category;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    @Override
    public FragmentFoodMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentFoodMainBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView() {
        database = PublicData.database ;
        category = database.getReference("Category");
        binding.recyclerFood.setLayoutManager(new GridLayoutManager(getContext(),2));

    }

    @Override
    protected void initData() {
        try {
            adapter = new FirebaseRecyclerAdapter<Category,
                    MenuViewHolder>(Category.class, R.layout.menu_item, MenuViewHolder.class, category) {
                @Override
                protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                    menuViewHolder.txtMenuName.setText(category.getName());
                Glide.with(getContext())
                        .load(category.getImage())
                        .error(R.drawable.img_no_image)
                        .into(menuViewHolder.imageView);
                    Category clickItem = category;
                    menuViewHolder.setInternClickListener((view, position, isLongCLick) -> {
                        Intent foodList = new Intent(getContext(), FoodListDetailActivity.class);
                        //Because CategoryId is key, so we just get key of this item
                        foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                        foodList.putExtra("CategoryName", clickItem.getName());
                        startActivity(foodList);
                        //get categoryId and send to new Activity
                    });
                }
            };
            binding.recyclerFood.setAdapter(adapter);
        } catch (Exception e){

        }

    }

    @Override
    public void onClick(View view) {

    }

    private void StartFoodList(String categoryId) {
        FoodDetailFragment fragment = new FoodDetailFragment(categoryId);
        getActivity().getSupportFragmentManager().beginTransaction()
//                .add(R.id.frame_container, null, "three")
                .replace(R.id.frame_container, fragment)
                .hide(this)
                .addToBackStack(null)
                .commit();
    }
}
