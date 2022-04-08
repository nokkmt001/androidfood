package com.example.fooddrink;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.fooddrink.Model.User;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivitySignUpBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignUpActivity extends BaseTestActivity<ActivitySignUpBinding> {
    @Override
    public ActivitySignUpBinding getViewBinding() {
        return ActivitySignUpBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void initView() {
        super.initView();
        FirebaseDatabase database = PublicData.database;
        DatabaseReference table_user = database.getReference("User");

        binding.btnSingUp.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.inputPhone.getText())) {
                showInfo("Bạn chưa nhập mật khẩu");
                return;
            }
            if (TextUtils.isEmpty(binding.inputPassword.getText())) {
                showInfo("Bạn chưa nhập mật khẩu");
                return;
            }
            if (TextUtils.isEmpty(binding.inputName.getText())) {
                showInfo("Bạn chưa nhập mật khẩu");
                return;
            }
            if (TextUtils.isEmpty(binding.inputAddress.getText())) {
                showInfo("Bạn chưa nhập mật khẩu");
                return;
            }
            showProgressDialog(true);
            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // check if already user phone
                    showProgressDialog(false);
                    String sdt = Objects.requireNonNull(binding.inputPhone.getText()).toString();
                    if (snapshot.child(sdt).exists()) {
                        showToast(getString(R.string.error_phone_is_exits));
                    } else {
                        User user = new User(Objects.requireNonNull(binding.inputName.getText()).toString(),
                                Objects.requireNonNull(binding.inputPassword.getText()).toString(),
                                Objects.requireNonNull(binding.inputAddress.getText()).toString());
                        table_user.child(sdt).setValue(user);
                        showToast(getString(R.string.title_sign_up_success));
                        startMain();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    showProgressDialog(false);
                    showToast(error.getMessage());
                }
            });
        });

    }

    private void startMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}