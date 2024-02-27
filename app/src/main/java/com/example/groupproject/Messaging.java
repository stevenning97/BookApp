package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import entities.UserProfile;

public class Messaging extends AppCompatActivity {
    UserProfile userprofile;
    Button sendMsg, readMess, btnPrevious;
    String userName;
    TextView textView, txtSender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        userName = userprofile.getUsername();
        DBHelper DB = new DBHelper(this);
        readMess = findViewById(R.id.btnReadMessage);
        txtSender = findViewById(R.id.txtSender);

        textView = findViewById(R.id.txtViewMess);
        readMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor = DB.getMess(userName);
                Cursor cNameSender = DB.getSender();
                if(cNameSender.getCount() > 0){
                    while (cNameSender.moveToNext()){
                        txtSender.setText(cNameSender.getString(1)  + " sent you a message");
                    }
                }

                if(cursor.getCount() > 0){
                    while (cursor.moveToNext()){
                        textView.setText(cursor.getString(2));
                    }
                }
                else {
                    Toast.makeText(Messaging.this,
                            "Nothing to display", Toast.LENGTH_SHORT).show();
                }

            }
        });
        sendMsg = (Button) findViewById(R.id.SendMsgBtn2);
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addMsgIntent = new Intent(Messaging.this,SendMessage.class);
                addMsgIntent.putExtra("userprofile",userprofile);
                startActivity(addMsgIntent);
                // startActivity(new Intent(CheckMessages.this,SendMessage.class));
            }
        });

        btnPrevious = findViewById(R.id.btnBacktoHome);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenBtnPrevious = new Intent(Messaging.this,Home.class);
                intenBtnPrevious.putExtra("userprofile",userprofile);
                startActivity(intenBtnPrevious);
            }
        });
    }
}