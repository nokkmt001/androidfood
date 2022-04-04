package com.example.fooddrink;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddrink.Interface.InternClickListener;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");
        recyclerView  = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //get intent here
        if (getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
            System.out.println(categoryId +".............................................");
        }
        if (!categoryId.isEmpty() && categoryId != null)
        {
            loadListFood(categoryId);
            System.out.println(categoryId +">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
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
                foodViewHolder.setInternClickListener(new InternClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongCick) {
                        Toast.makeText(FoodList.this,"" + local.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        //set adapter
        recyclerView.setAdapter(adapter);

    }
}
// này là danh sách món nào đó