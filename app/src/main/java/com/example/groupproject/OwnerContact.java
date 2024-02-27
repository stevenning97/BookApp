package com.example.groupproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import entities.UserProfile;

public class OwnerContact extends AppCompatActivity {
    UserProfile userprofile;
    DBHelper myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_contact);

        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");

        Button btnRequestOwner = (Button) findViewById(R.id.btnRequestOwner);
        ListView listView = (ListView) findViewById(R.id.ListOwnerContact);
        Bundle bundle = getIntent().getExtras();
        myDatabase = new DBHelper(this);
        if(bundle!= null) {
            String bookTitleData = bundle.getString("bookTitleData");
            //ArrayList<String> bookList = new ArrayList<>();
            ArrayList<String> ownerList = new ArrayList<>();
            Cursor data = myDatabase.viewBookOwner(bookTitleData);
            if (data.getCount() > 0) {
                while (data.moveToNext()) {
                    ownerList.add(data.getString(0));

                    ListAdapter listOwnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ownerList);
                    listView.setAdapter(listOwnerAdapter);
                /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int itemPosition = i;
                        String value = (String) listView.getItemAtPosition(itemPosition);
                        Intent ownerContactScreen = new Intent(ListBooks.this, OwnerContact.class);
                        ListBooks.this.startActivity(ownerContactScreen);

                });*/
                    //str.append(" BookTitle : " + c.getString(0));
                    //str.append(" OwnerContact : " + c.getString(1));
                    //str.append(" Id : " + c.getString(2));
                        /*str.append(" cell : " + c.getString(3));
                        str.append(" provname: " + c.getString(3));*/
                    //str.append("\n");
                }
                //txtOutput.setText(str);
                //"Please click on the desired book to contact the Owner"
                // String[] postalCodeArray ={"V1M 3B5", "V2Y 1A5", "V3A 5G2", "V3R0A4", "V3M 5Z5"};
            } else {
                Toast.makeText(OwnerContact.this,
                        "Sorry, owner's contact information is not available", Toast.LENGTH_SHORT).show();
            }
        }

        //btnRequestOwner = findViewById(R.id.btnCheckmsg);
        btnRequestOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent btnRequestOwnermsg = new Intent(OwnerContact.this, Messaging.class);
                btnRequestOwnermsg.putExtra("userprofile",userprofile );
                startActivity(btnRequestOwnermsg);*/
                Intent requestOwnermsgScreen = new Intent(getApplicationContext(), Messaging.class);
                requestOwnermsgScreen.putExtra("userprofile", userprofile);
                OwnerContact.this.startActivity(requestOwnermsgScreen);
               // startActivity(new Intent(OwnerContact.this, Messaging.class));
            }
        });

        /*btnRequestOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnRequestOwnerIntent = new Intent(OwnerContact.this, Messaging.class);
                btnRequestOwnerIntent.putExtra("userprofile",userprofile);
                startActivity(btnRequestOwnerIntent);
                //startActivity(new Intent(OwnerContact.this, Messaging.class));
            }
        });*/

        }
}