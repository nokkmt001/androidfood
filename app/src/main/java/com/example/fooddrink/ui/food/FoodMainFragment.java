package com.example.fooddrink.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.Category;
import com.example.fooddrink.R;
import com.example.fooddrink.ViewHolder.MenuViewHolder;
import com.example.fooddrink.databinding.FragmentFoodMainBinding;
import com.example.fooddrink.ui.base.BaseFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodMainFragment extends BaseFragment<FragmentFoodMainBinding> {
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";
    DatabaseReference category;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    @Override
    public FragmentFoodMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentFoodMainBinding.inflate(inflater);
    }

    @Override
    protected void initView() {
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");
        binding.recyclerFood.setLayoutManager(new GridLayoutManager(getContext(),2));
    }

    @Override
    protected void initData() {
        adapter = new FirebaseRecyclerAdapter<Category,
                MenuViewHolder>(Category.class, R.layout.menu_item, MenuViewHolder.class, category) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                menuViewHolder.txtMenuName.setText(category.getName());
                Picasso.get().load(category.getImage()).into(menuViewHolder.imageView);
                Glide.with(getContext())
                        .load(category.getImage())
                        .error(R.drawable.img_no_image)
                        .into(menuViewHolder.imageView);
                Category clickItem = category;
                menuViewHolder.setInternClickListener((view, position, isLongCLick) -> {

                    //get categoryId and send to new Activity
//                    Intent foodList = new Intent(this, FoodList.class);
                    //Because CategoryId is key, so we just get key of this item
//                    foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
//                    startActivity(foodList);
                });
            }
        };
        binding.recyclerFood.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    private void StartFullProduct() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, null, "three")
                .hide(this)
                .addToBackStack(null)
                .commit();
    }
}
