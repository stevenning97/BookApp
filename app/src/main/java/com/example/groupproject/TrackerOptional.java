package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import entities.UserProfile;

public class TrackerOptional extends AppCompatActivity {
    Button btnViewYourBook, btnViewYourTracker, btnAddTracker, btnPrevious;
    private UserProfile userprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_optional);
        loadData();
        btnViewYourBook = findViewById(R.id.btnViewOweBook);
        btnViewYourBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnReadingTracker = new Intent(TrackerOptional.this, ReadingTracker.class);
                btnReadingTracker.putExtra("userprofile",userprofile);
                startActivity(btnReadingTracker);
            }
        });

        btnAddTracker = findViewById(R.id.btnAddBookTracker);
        btnAddTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnAddTracker = new Intent(TrackerOptional.this, TrackBook.class);
                btnAddTracker.putExtra("userprofile",userprofile);
                startActivity(btnAddTracker);
            }
        });

        btnViewYourTracker = findViewById(R.id.btnViewBookTracker);
        btnViewYourTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackerOptional.this, BookTrackerInfo.class);
                intent.putExtra("userprofile", userprofile);
                startActivity(intent);
            }
        });

        btnPrevious = findViewById(R.id.btnHomeprevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackerOptional.this, Home.class);
                intent.putExtra("userprofile", userprofile);
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");

    }
}