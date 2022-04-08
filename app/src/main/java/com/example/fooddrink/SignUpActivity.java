package com.example.fooddrink;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddrink.Model.User;
import com.example.fooddrink.database.PublicData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText edtPhone, edtName, edtPassword;
    Button btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName     = (MaterialEditText) findViewById(R.id.edtName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone    = (MaterialEditText) findViewById(R.id.edtPhone);
        btnSingUp   = (Button) findViewById(R.id.btnSingUp);

        // init firebase
        FirebaseDatabase database = PublicData.database ;
        DatabaseReference table_user = database.getReference("User");

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog mdialog = new ProgressDialog(SignUpActivity.this);
                mdialog.setMessage("Please waiting...!");
                mdialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // check if already user phone
                        if (snapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mdialog.dismiss();
                            Toast.makeText(SignUpActivity.this,"Phone Number Already Register",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            mdialog.dismiss();
                            User user = new User(edtName.getText().toString(),edtPassword.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this,"Sign Up Successfully",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}