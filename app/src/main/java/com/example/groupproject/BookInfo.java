package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import entities.UserProfile;

public class BookInfo extends AppCompatActivity {
    int userid;
    UserProfile userprofile;
    ImageView imageView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        setTitle("Book Information");

        DBHelper  dbHelper= new DBHelper(this);

        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        userid = userprofile.getUserId();
        imageView = (ImageView) findViewById(R.id.imgBookLarge);
        String bookID = intent.getStringExtra("bookID");

        //imageView.setImageResource(adapter.getImage(position));
        TextView txtBookNameInfo = (TextView) findViewById(R.id.txtBookNameInfo);
        TextView txtAuthorInfo = (TextView) findViewById(R.id.txtAuthorInfo);
        TextView txtStatusInfo = (TextView) findViewById(R.id.txtBookStatusInfo);
        TextView txtPublisherInfo = (TextView) findViewById(R.id.txtPublisherInfo);
        TextView txtPublicationYearInfo = (TextView) findViewById(R.id.txtPublicationYearInfo);
        TextView txtPriceInfo = (TextView) findViewById(R.id.txtPriceInfo);
        Button btnUpdateInfo = (Button) findViewById(R.id.btnUpdateInfo);
        Button btnCancelInfo = (Button) findViewById(R.id.btnCancelInfo);

        btnCancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnCancelIntent = new Intent(BookInfo.this, BookList.class);
                btnCancelIntent.putExtra("userprofile",userprofile);
                startActivity(btnCancelIntent);

            }
        });

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookInfoIntent = new Intent(BookInfo.this, UpdateBook.class);
                bookInfoIntent.putExtra("userprofile",userprofile);
                bookInfoIntent.putExtra("bookID",bookID);
                startActivity(bookInfoIntent);
            }
        });

        Cursor c  = dbHelper.getSingleBookInfo(Integer.valueOf(bookID));

        if(c.getCount()>0){
            while(c.moveToNext()){
                txtBookNameInfo.setText(c.getString(1));
                txtAuthorInfo.setText("Author " +c.getString(2));
                txtStatusInfo.setText(c.getString(9));
                txtPublisherInfo.setText("Publisher " +c.getString(3));
                txtPublicationYearInfo.setText("Published Year "+c.getString(4));
                txtPriceInfo.setText("$ " + c.getString(10));
                imageView.setImageResource(getResources().getIdentifier(c.getString(8),"drawable", getPackageName()));
            }
        }



    }
}