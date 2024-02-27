package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import entities.UserProfile;

// Done by Steven Ning-300324107 - For login
// Done by Thanh Nhan Phan-300303290- For User database

public class Register extends AppCompatActivity {

    EditText username,email, address, age, password, password2;
    Button register, login;
    Spinner interest;
    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.btnRegister);

        username = (EditText) findViewById(R.id.txtName1);
        email = (EditText) findViewById(R.id.txtPastalCode1);
        address = (EditText) findViewById(R.id.txtPostalCode);
        age = (EditText) findViewById(R.id.txtAge1);
        interest = (Spinner) findViewById(R.id.spInterest);
        password = (EditText) findViewById(R.id.txtPassword1);
        password2 = (EditText) findViewById(R.id.txtConfirmPass);
        register = (Button) findViewById(R.id.btnCancelChange);
        login = (Button) findViewById(R.id.btnSave);

        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfile userProfile = new UserProfile();
                userProfile.setUsername(username.getText().toString());
                userProfile.setEmail(email.getText().toString());
                userProfile.setAddress(address.getText().toString());
                userProfile.setAge(age.getText().toString());
                userProfile.setInterest(interest.getSelectedItem().toString());
                userProfile.setPassword(password.getText().toString());
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String pass2 = password2.getText().toString();



                if(user.equals("") || pass.equals("") || pass2.equals("")){
                    // if username,password or password confirmation is blank tells user to fill out all required fields

                    Toast.makeText(Register.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!pass.equals(pass2))
                    {
                        password2.setError("Passwords do not match");
                    }
                    else{
                        if(DB.checkusername(user))
                        {
                            Toast.makeText(Register.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            DB.insertData(userProfile);
                            Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });



    }


}