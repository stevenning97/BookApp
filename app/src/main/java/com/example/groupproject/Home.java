package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import entities.UserProfile;

public class Home extends AppCompatActivity {
        private TextView welcomeText;
        private Button btnReadingTracker, btnViewProfile, btnlogout, btnViewBooks, addBook, btnMess;
        private UserProfile userprofile;
        private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(R.string.welcome);

        welcomeText = findViewById(R.id.txtWelcome);

        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        welcomeText.setText("Welcome " + userprofile.getUsername());

        addBook = (Button)findViewById(R.id.btnAddUpdate);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Home.this, AddingBook.class));
                Intent btnAddBookIntent = new Intent(Home.this, AddingBook.class);
                btnAddBookIntent.putExtra("userprofile",userprofile);
                startActivity(btnAddBookIntent);
            }
        });

        btnViewBooks = (Button) findViewById(R.id.btnViewBooks);
        btnViewBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnAddBookIntent = new Intent(Home.this, BookList.class);
                btnAddBookIntent.putExtra("userprofile",userprofile);
                startActivity(btnAddBookIntent);
            }
        });

        Button borrowBook = (Button)findViewById(R.id.btnBorrow);
        borrowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnBorrowBookIntent = new Intent(Home.this, BorrowBook.class);
                btnBorrowBookIntent.putExtra("userprofile",userprofile);
                startActivity(btnBorrowBookIntent);
            }
        });

        session = new Session(this);
        if (!session.loggedin()) {
            logout();
        }
        btnlogout = (Button) findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btnViewProfile = findViewById(R.id.btnViewProfile);

        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnViewIntent = new Intent(Home.this, ViewProfile.class);
                btnViewIntent.putExtra("userprofile",userprofile);
                startActivity(btnViewIntent);

            }
        });
        btnReadingTracker = findViewById(R.id.btnReadingTracker);
        btnReadingTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnReadingTracker = new Intent(Home.this, TrackerOptional.class);
                btnReadingTracker.putExtra("userprofile",userprofile);
                startActivity(btnReadingTracker);
            }
        });
        btnMess = findViewById(R.id.btnCheckmsg);
        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnCheckmsg = new Intent(Home.this, Messaging.class);
                btnCheckmsg.putExtra("userprofile",userprofile );
                startActivity(btnCheckmsg);
            }
        });
    }

    private void logout(){
        session.setLoggedin(false);
        startActivity(new Intent(Home.this,Login.class));
    }
}