package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entities.UserProfile;

public class SendMessage extends AppCompatActivity {
    EditText message, username;
    Button senMsg, btnPrevious;
    int userId;
    UserProfile userprofile;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        message = findViewById(R.id.sendMsgtxt);
        username = findViewById(R.id.usernamemsg);


        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");


        DB = new DBHelper(this);

        senMsg = findViewById(R.id.btnSendMess);
        senMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String msg = message.getText().toString();
                userId= userprofile.getUserId();
                Boolean addData = DB.insertMessData(user,msg,userId);
                if(addData){
                    Toast.makeText(SendMessage.this,"Message sent", Toast.LENGTH_SHORT).show();
                    Intent addMgsBtn = new Intent(SendMessage.this, Messaging.class);
                    addMgsBtn.putExtra("userprofile",userprofile);
                    startActivity(addMgsBtn);
                }
                else {
                    Toast.makeText(SendMessage.this,"Message failed to send", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPrevious = findViewById(R.id.btnPreviousToHome);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnPrevious = new Intent(SendMessage.this, Messaging.class);
                btnPrevious.putExtra("userprofile",userprofile);
                startActivity(btnPrevious);
            }
        });
    }
}

