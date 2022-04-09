package com.example.fooddrink.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fooddrink.Model.Food;
import com.example.fooddrink.databinding.ItemCartBinding;
import com.example.fooddrink.ui.base.BaseLMAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseLMAdapter<Food, ItemCartBinding> {
    OnClick onClick;
    Context mContext;
    private List<Food> listChose= new ArrayList<>();

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public CartAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    public void checkAllData(boolean isChecked, List<Food> list) {
        listChose = new ArrayList<>();
        for (Food item : list) {
            item.setCheck(isChecked);
            if (isChecked)
                listChose.add(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public void setupViews(ItemCartBinding binding, Food item, int position) {
        binding.textTitle.setText(item.getName());
        binding.textCount.setText(item.getCount().toString());

        Glide.with(mContext)
                .load(item.getImage())
                .override(300, 300)
                .into(binding.imageLogo);
        binding.textPrice.setText(item.getPrice());

        binding.imageAdd.setOnClickListener(view -> onClick.onClick(view, position));

        binding.imageMinus.setOnClickListener(view -> onClick.onClick(view, position));

    }

    @Override
    public ItemCartBinding getViewBinding(ViewGroup parent, int viewType) {
        return ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()));
    }

}
