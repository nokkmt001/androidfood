package com.example.fooddrink.ui.cart;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fooddrink.Model.Food;
import com.example.fooddrink.R;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivityCartBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.booking.BookingActivity;

public class CartActivity extends BaseTestActivity<ActivityCartBinding> {
    CartAdapter adapter;

    @Override
    public ActivityCartBinding getViewBinding() {
        return ActivityCartBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        adapter = new CartAdapter(this);
        binding.rylCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rylCart.setAdapter(adapter);
        binding.buttonOK.setOnClickListener(view -> {
            if (PublicData.listDataFoodBooking == null) return;
            AppPreference.saveListFoodBooking(PublicData.listDataFoodBooking);
            Intent intent = new Intent(this, BookingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        adapter.setOnClick((view, position) -> {
            Food info = adapter.getItem(position);
            Integer count = info.getCount();
            switch (view.getId()) {
                case R.id.imageAdd:
                    count = count + 1;
                    info.setCount(count);
                    adapter.remoteItem(position);
                    adapter.add(info, position);
                    break;
                case R.id.imageMinus:
                    count = count - 1;
                    info.setCount(count);
                    adapter.remoteItem(position);
                    if (count != 0) {
                        adapter.add(info, position);
                    }
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void initData() {
        if (PublicData.listDataFoodBooking != null) {
            adapter.clearData();
            adapter.addData(PublicData.listDataFoodBooking);
        }
    }

    @Override
    public void onClick(View view) {

    }

}
