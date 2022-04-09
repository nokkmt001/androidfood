package com.example.fooddrink.utils;

import android.view.View;

import com.bumptech.glide.Glide;
import com.example.fooddrink.R;
import com.example.fooddrink.databinding.ActivityViewImageBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;

public class ViewImageActivity extends BaseTestActivity<ActivityViewImageBinding> {

    @Override
    public ActivityViewImageBinding getViewBinding() {
        return ActivityViewImageBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        super.initView();
        String url = getIntent().getExtras().getString("PATHIMAGE");
        binding.rlBack.setOnClickListener(view -> finish());

        Glide.with(this)
                .load(url)
                .error(R.drawable.img_no_image)
                .into(binding.imageView);
    }

    @Override
    public void onClick(View view) {

    }
}