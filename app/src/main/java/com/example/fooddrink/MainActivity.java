package com.example.fooddrink;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;

import com.example.fooddrink.databinding.ActivityMainBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;

public class MainActivity extends BaseTestActivity<ActivityMainBinding> {

    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Nabila.ttf");
        binding.textSlogan.setTypeface(face);

        binding.btnSingUp.setOnClickListener(view -> {
            Intent signup = new Intent(this, SignUpActivity.class);
            startActivity(signup);
        });

        binding.btnSingIn.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}