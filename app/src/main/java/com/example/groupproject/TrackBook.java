package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import entities.UserProfile;

public class TrackBook extends AppCompatActivity {
    //To track the date user add book
    Calendar calendarTrack = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String dateTrack = df.format(calendarTrack.getTime());

    int userId;
    private UserProfile userprofile;

    EditText bookName, pageNum;
    Button btnSubmit, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_book);

        bookName = findViewById(R.id.txtBookName);
        pageNum = findViewById(R.id.txtPageNum);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancelSub);

        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        userId = userprofile.getUserId();

        DBHelper  DB= new DBHelper(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnCancelIntent = new Intent(TrackBook.this, TrackerOptional.class);
                btnCancelIntent.putExtra("userprofile",userprofile);
                startActivity(btnCancelIntent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookname = bookName.getText().toString();
                int pagenum = Integer.parseInt(pageNum.getText().toString()) ;
                if(bookname.equals("") || pagenum == 0)
                {
                    Toast.makeText(TrackBook.this,"Please enter details to all the required fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean addTrack = DB.insertTrackData(bookname, pagenum, dateTrack, userId);
                    if(addTrack){
                        Toast.makeText(TrackBook.this,"Tracker is Successfully Added", Toast.LENGTH_SHORT).show();
                        Intent addTracker = new Intent(TrackBook.this, TrackerOptional.class);
                        addTracker.putExtra("userprofile",userprofile);
                        startActivity(addTracker);
                    }
                    else {
                        Toast.makeText(TrackBook.this,"Track is not added. Please try again.", Toast.LENGTH_SHORT).show();
                        Intent errorIntent = new Intent(TrackBook.this, TrackerOptional.class);
                        errorIntent.putExtra("userprofile",userprofile);
                        startActivity(errorIntent);
                    }
                }
            }
        });

    }
}