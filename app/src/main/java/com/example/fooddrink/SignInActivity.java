package com.example.fooddrink;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.fooddrink.Model.User;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivitySignInBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends BaseTestActivity<ActivitySignInBinding> {

    @Override
    public ActivitySignInBinding getViewBinding() {
        return ActivitySignInBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        FirebaseDatabase database = PublicData.database ;
        DatabaseReference table_user = database.getReference("User");
        binding.btnSingIn.setOnClickListener(view -> {
            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot snapshot) {
                    //check if user not exist in database
                    if (snapshot.child(binding.edtPhone.getText().toString()).exists()) {
                        //get user info
                        User user = snapshot.child(binding.edtPhone.getText().toString()).getValue(User.class);
                        assert user != null;
                        if (user.getPassword().equals(binding.edtPassword.getText().toString())) {
                            AppPreference.setLogin(true);
                            AppPreference.setUserPhone(binding.edtPhone.getText().toString());
                            AppPreference.setUserPass(binding.edtPassword.getText().toString());
                            // save to sharedPreferences
                            Intent homeIntent = new Intent(SignInActivity.this, HomeActivity.class);
                            PublicData.currentUser = user;
                            AppPreference.saveUserMain(user);
                            // save user current in to cache for uses another class
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, R.string.title_sign_in_failed, Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(SignInActivity.this, R.string.title_user_not_exist, Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError Error) {

                }
            });
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}