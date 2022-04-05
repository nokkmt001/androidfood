package com.example.fooddrink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddrink.Common.Common;
import com.example.fooddrink.Model.User;
import com.example.fooddrink.database.AppPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = findViewById(R.id.edtPassword);
        edtPhone    = findViewById(R.id.edtPhone);
        btnSignIn   = findViewById(R.id.btnSingIn);

        // init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");
        btnSignIn.setOnClickListener(view -> {
            ProgressDialog mdialog = new ProgressDialog(SignIn.this);
            mdialog.setMessage("Please waiting...!");
            mdialog.show();
            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot snapshot) {
                    //check if user not exist in database
                    if (snapshot.child(edtPhone.getText().toString()).exists()) {
                        //get user info
                        mdialog.dismiss();
                        User user = snapshot.child(edtPhone.getText().toString()).getValue(User.class);
                        if (user.getPassword().equals(edtPassword.getText().toString())) {
                            AppPreference.setUser(edtPhone.getText().toString());
                            AppPreference.setUserPass(edtPassword.getText().toString());
                            Intent homeIntent = new Intent(SignIn.this, HomeActivity.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Toast.makeText(SignIn.this, "Sign In failed! Please check phone number or password", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        mdialog.dismiss();
                        Toast.makeText(SignIn.this,"User not exist in database", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError Error) {

                }
            });
        });
    }
}