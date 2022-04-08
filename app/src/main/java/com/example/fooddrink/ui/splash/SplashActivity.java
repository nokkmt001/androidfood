package com.example.fooddrink.ui.splash;

import static com.example.fooddrink.utils.AppConstant.DelayData;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fooddrink.HomeActivity;
import com.example.fooddrink.MainActivity;
import com.example.fooddrink.Model.User;
import com.example.fooddrink.R;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivitySplashBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends BaseTestActivity<ActivitySplashBinding> {
    FirebaseDatabase database;
    DatabaseReference table_user;
    String numberPhone = "";
    String pass = "";

    @Override
    public ActivitySplashBinding getViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        database = PublicData.database ;
        table_user = database.getReference("User");

        new Handler().postDelayed(() -> {
            if (AppPreference.isLogin()) {
                numberPhone = AppPreference.getUserPhone();
                pass = AppPreference.getUserPass();
                showCheck();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DelayData);
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View view) {

    }

    private void showCheck() {
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //check if user not exist in database
                if (snapshot.child(numberPhone).exists()) {
                    //get user info
                    User user = snapshot.child(numberPhone).getValue(User.class);
                    assert user != null;
                    if (user.getPassword().equals(pass)) {
                        AppPreference.setUserPhone(numberPhone);
                        AppPreference.setUserPass(pass);
                        Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
                        PublicData.currentUser = user;
                        startActivity(homeIntent);
                        finish();
                    } else {
                        Toast.makeText(SplashActivity.this, R.string.title_sign_in_failed, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SplashActivity.this,  R.string.title_user_not_exist,  Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError Error) {

            }
        });
    }

}