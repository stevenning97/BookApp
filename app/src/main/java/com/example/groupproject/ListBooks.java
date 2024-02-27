package com.example.groupproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import entities.UserProfile;

public class ListBooks extends AppCompatActivity {
    UserProfile userprofile;
    StringBuilder str = new StringBuilder();
    DBHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);

        //TextView txtOutput = findViewById(R.id.txtOutputDb);
        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");

        TextView userPrompt = findViewById(R.id.txtOwnerPrompt);
        ListView listView = (ListView) findViewById(R.id.listView);
        Bundle bundle = getIntent().getExtras();
        //TextView testPostalAndActivity = findViewById(R.id.testPostalAndActivity);
        //To handle a case when no data is passed
        if(bundle!= null) {
            String postalData = bundle.getString("postalData");
            String borrowActivityData = bundle.getString("borrowActivityData");
            //String testString =  "Below book is avaliable at "+ postalData + " for the " + borrowActivityData;

            //txtOutput.setText(testString);

            myDB = new DBHelper(this);

            ArrayList<String> bookList = new ArrayList<>();
           // ArrayList<String> ownerList = new ArrayList<>();
            Cursor data = myDB.viewNearByAvailableBooks(postalData, borrowActivityData);

            if (data.getCount() > 0) {
                while (data.moveToNext()) {
                    bookList.add(data.getString(0));

                    ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
                    listView.setAdapter(listAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            int itemPosition = i;
                            String value = (String) listView.getItemAtPosition(itemPosition);
                            //Intent ownerContactScreen = new Intent(ListBooks.this, OwnerContact.class);
                            Intent ownerContactScreen = new Intent(getApplicationContext(), OwnerContact.class);
                            ownerContactScreen.putExtra("bookTitleData", value);
                            ownerContactScreen.putExtra("userprofile", userprofile);
                            ListBooks.this.startActivity(ownerContactScreen);
                        }
                    });
                    /*str.append(" BookTitle : " + data.getString(0));
                    str.append(" PostalCode : " + postalData);
                    str.append(" BorrowActivity : " + bundle.getString("borrowActivityData"));
                        /*str.append(" cell : " + c.getString(3));
                        str.append(" provname: " + c.getString(3));
                    str.append("\n");*/
                }
                //txtOutput.setText(str);
                //"Please click on the desired book to contact the Owner"
                // String[] postalCodeArray ={"V1M 3B5", "V2Y 1A5", "V3A 5G2", "V3R0A4", "V3M 5Z5"};
            } else {
                userPrompt.setText("");
                Toast.makeText(ListBooks.this,
                        "Sorry, no neraby books available. Please try with other options or postal code", Toast.LENGTH_LONG).show();
            }

        }
    }
}