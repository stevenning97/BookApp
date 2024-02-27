package com.example.groupproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import entities.UserProfile;

public class BookTrackerInfo extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;

    DBHelper DB;
    ArrayList<String> tracker_id, book_name, page_num, publish_date;
    CustomAdapterTracker customAdapter;
    UserProfile userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tracker_info);

        recyclerView = findViewById(R.id.recyclerView1);

        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);


        DB = new DBHelper(BookTrackerInfo.this);
        tracker_id = new ArrayList<>();
        book_name = new ArrayList<>();
        page_num = new ArrayList<>();
        publish_date = new ArrayList<>();
        loadData();

        storeDataInArrays();

        customAdapter = new CustomAdapterTracker(BookTrackerInfo.this,this, tracker_id, book_name, page_num,
                publish_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookTrackerInfo.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){

        Cursor cursor = DB.readTrackerData(userprofile.getUserId());
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                tracker_id.add(cursor.getString(0));
                book_name.add(cursor.getString(1));
                page_num.add(cursor.getString(2));
                publish_date.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
    private void loadData(){
        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");

    }







}