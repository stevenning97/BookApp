package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import entities.UserProfile;

public class ViewProfile extends AppCompatActivity {
    TextView username,email, address, age, password,interest;
    Button btnChangeProfile, btnPrevious;
    UserProfile userprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        setTitle("View your Profile");

        username = findViewById(R.id.txtName1);
        email = findViewById(R.id.txtEmail1);
        address = findViewById(R.id.txtPastalCode1);
        age = findViewById(R.id.txtAge1);
        interest = findViewById(R.id.txtInterest1);
        password = findViewById(R.id.txtPassword1);

        loadData();

        btnPrevious = findViewById(R.id.btnPrevious);
        btnChangeProfile = findViewById(R.id.btnChangeProfile);
        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBtnChange = new Intent(ViewProfile.this, EditProfile.class);
                intentBtnChange.putExtra("userprofile", userprofile);
                startActivity(intentBtnChange);
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfile.this, Home.class);
                intent.putExtra("userprofile", userprofile);
                startActivity(intent);

            }
        });
    }
    private void loadData(){
        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        username.setText(userprofile.getUsername());
        email.setText(userprofile.getEmail());
        address.setText(userprofile.getAddress());
        age.setText(userprofile.getAge());
        interest.setText(userprofile.getInterest());
        password.setText(userprofile.getPassword());
    }
}