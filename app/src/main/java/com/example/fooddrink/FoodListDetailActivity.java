package com.example.fooddrink;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddrink.Model.Food;
import com.example.fooddrink.ViewHolder.FoodViewHolder;
import com.example.fooddrink.databinding.ActivityFoodListBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodListDetailActivity extends BaseTestActivity<ActivityFoodListBinding> {
    RecyclerView recyclerView;
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
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //get intent here
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
            categoryName = getIntent().getStringExtra("CategoryName");
            if (categoryName !=null){

            }
            System.out.println(categoryId + ".............................................");
        }
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFood(categoryId);
            System.out.println(categoryId + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
    }

    @Override
    protected void initData() {

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
                Picasso.get().load(food.getImage()).into(foodViewHolder.food_image);

                Food local = food;
                foodViewHolder.setInternClickListener((view, position, isLongCick) -> Toast.makeText(FoodListDetailActivity.this, "" + local.getName(), Toast.LENGTH_SHORT).show());
            }
        };
        //set adapter
        binding.recyclerFood.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }
}
// này là danh sách món nào đó