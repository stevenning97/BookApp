package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Arrays;

import entities.UserProfile;


public class BookList extends AppCompatActivity implements ImageAdapter.ItemClickListener{


    Integer[] bookIDs;
    String[] bookImages;
    String[] bookTitles;
    String[] bookStatus;
    String[] bookOwner;
    Integer[] imageID;
    ImageAdapter adapter;

    int userid;
    UserProfile userprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        setTitle("Book Information");

        DBHelper  dbHelper= new DBHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recylcerView);
        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        userid = userprofile.getUserId();
        Integer[] coverBook ={R.drawable.coverbook};

        int numOfCol = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this,numOfCol));


        Cursor c = dbHelper.getBookInfo(userid);

        int count = c.getCount();
        bookImages = new String[c.getCount()];
        bookTitles = new String[c.getCount()];
        bookOwner = new String[c.getCount()];
        bookStatus = new String[c.getCount()];
        bookIDs = new Integer[c.getCount()];
        if(c.getCount()>0 )
        {
            while (c.moveToNext()) {
                bookImages[c.getPosition()] = c.getString(8);
                bookIDs[c.getPosition()] = c.getInt(0);
                bookTitles[c.getPosition()] = c.getString(1);
                bookOwner[c.getPosition()] = c.getString(14);
                bookStatus[c.getPosition()] = c.getString(9);
            }
        }
        else{
            Toast.makeText(BookList.this,
                    "nothing to display", Toast.LENGTH_SHORT).show();
            Intent btnAddBookIntent = new Intent(BookList.this, Home.class);
            btnAddBookIntent.putExtra("userprofile",userprofile);


        }
        imageID = new Integer[bookImages.length];

        for (int i = 0; i < bookImages.length; i++) {

            try {

                imageID[i] = getResId(bookImages[i], R.drawable.class);
                adapter = new ImageAdapter(this, imageID,bookTitles,bookStatus,bookOwner,bookIDs);
                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onItemClick(View view, int position) {

        Intent btnAddBookIntent = new Intent(BookList.this, BookInfo.class);
        btnAddBookIntent.putExtra("userprofile",userprofile);
        btnAddBookIntent.putExtra("bookID",bookIDs[position].toString());
        //btnAddBookIntent.putExtra("bookImage",adapter.getImage(position));
        startActivity(btnAddBookIntent);

    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}