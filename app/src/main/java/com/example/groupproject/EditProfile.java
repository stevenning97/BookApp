package com.example.groupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import entities.UserProfile;

public class EditProfile extends AppCompatActivity {
    EditText username,email, address, age, password;
    Button btnUpdate, btnCancel;
    Spinner interest;
    UserProfile userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");



        username = findViewById(R.id.txtName1);
        email = findViewById(R.id.txtPastalCode1);
        address = findViewById(R.id.txtPostalCode);
        age =  findViewById(R.id.txtAge1);
        interest = findViewById(R.id.spInterest);
        password = findViewById(R.id.txtPassword1);

        loadData();

        btnCancel = findViewById(R.id.btnCancelChange);
        btnUpdate = findViewById(R.id.btnSave);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonUpdate_onClick(view);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, Home.class);
                intent.putExtra("userprofile", userprofile);
                startActivity(intent);

            }
        });
    }

    public void buttonUpdate_onClick(View view) {
        try {
                DBHelper DB = new DBHelper(getApplicationContext());
                UserProfile currentUser = DB.find(userprofile.getUserId());
                String newUsername = username.getText().toString();

                UserProfile temp = DB.checkuser(newUsername);

                if(!newUsername.equalsIgnoreCase(currentUser.getUsername()) && temp !=null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(R.string.error);
                    builder.setMessage(R.string.useExits);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                    return;
                }

                    currentUser.setUsername(username.getText().toString());
                    currentUser.setEmail(email.getText().toString());
                    currentUser.setAddress(address.getText().toString());
                    currentUser.setAge(age.getText().toString());
                    currentUser.setInterest(interest.getSelectedItem().toString());
                    String passWord = password.getText().toString();
                    if(passWord.isEmpty()){
                        currentUser.setPassword(password.getText().toString());
                    }
                    if(DB.updateData(currentUser)){
                        Intent intent = new Intent(EditProfile.this, Home.class);
                        intent.putExtra("userprofile", currentUser);
                        startActivity(intent);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle(R.string.error);
                        builder.setMessage(R.string.errorChange);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                    }



        }
        catch (Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.invalid);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
    }

    private void loadData(){
        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        username.setText(userprofile.getUsername());
        email.setText(userprofile.getEmail());
        address.setText(userprofile.getAddress());
        age.setText(userprofile.getAge());
        password.setText(userprofile.getPassword());
    }
}