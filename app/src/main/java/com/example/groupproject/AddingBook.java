package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import entities.UserProfile;

public class AddingBook extends AppCompatActivity {

    int SELECT_PICTURE = 200;
    ImageView imgBook;
    String borrowActivityRent = "";
    String borrowActivityShare = "";
    String borrowActivityGiveaway = "";
    int userid;
    UserProfile userprofile;

    //To track the date user add book
    Calendar calendarTrack = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String dateTrack = df.format(calendarTrack.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_book);

        EditText txtBookTitle = (EditText) findViewById(R.id.txtBookName);
        EditText txtAuthorName = (EditText) findViewById(R.id.txtPageNum);
        EditText txtPubisherName = (EditText) findViewById(R.id.txtDate);
        EditText txtPublicationYear =(EditText) findViewById(R.id.txtPublicationYear);
        EditText txtBookImageName =(EditText) findViewById(R.id.txtbookImageName);
        EditText txtRentPrice = (EditText) findViewById(R.id.txtRentPrice);
        CheckBox chkShare = (CheckBox) findViewById(R.id.chkShare);
        CheckBox chkRent = (CheckBox) findViewById(R.id.chkRent);
        CheckBox chkGiveAway = (CheckBox) findViewById(R.id.chkGiveaway);

        final Button btnAddBook = (Button) findViewById(R.id.btnSubmit);
        final Button btnCancel = (Button) findViewById(R.id.btnCancelSub);

        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        userid = userprofile.getUserId();

        DBHelper  dbHelper= new DBHelper(this);
        txtRentPrice.setEnabled(false);

        chkRent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtRentPrice.setEnabled(true);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnCancelIntent = new Intent(AddingBook.this, Home.class);
                btnCancelIntent.putExtra("userprofile",userprofile);
                startActivity(btnCancelIntent);
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookTitle = txtBookTitle.getText().toString();
                String author = txtAuthorName.getText().toString();
                String publisher = txtPubisherName.getText().toString();
                String publicationYear = txtPublicationYear.getText().toString();
                String bookImageName = txtBookImageName.getText().toString();
                float rentPrice;

//                String bookImageURL = "R.drawable."+bookImageName;
                if(txtRentPrice.getText().toString() != null && txtRentPrice.getText().toString().length() >0)
                {
                    rentPrice = Float.parseFloat(txtRentPrice.getText().toString());
                }
                else
                {
                    rentPrice = 0;
                }

                if(chkShare.isChecked())
                {
                    borrowActivityShare = "Yes";
                }
                else {
                    borrowActivityShare = "No";
                }
                if(chkRent.isChecked())
                {
                    borrowActivityRent = "Yes";

                }
                else{
                    borrowActivityRent = "No";
                }
                if(chkGiveAway.isChecked())
                {
                    borrowActivityGiveaway = "Yes";

                }
                else {
                    borrowActivityGiveaway = "No";
                }

                if(bookTitle.equals("") || author.equals("") || bookImageName.equals(""))
                {
                    Toast.makeText(AddingBook.this,"Please enter details to all the required fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!chkShare.isChecked() && !chkRent.isChecked() && !chkGiveAway.isChecked())
                    {
                        Toast.makeText(AddingBook.this,"Please Select at least one borrow activity ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(chkRent.isChecked() && rentPrice==0)
                        {
                            Toast.makeText(AddingBook.this,"Please Enter the rent amount", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Boolean insert = dbHelper.insertBookData(bookTitle,author,publisher,publicationYear,borrowActivityShare,borrowActivityRent,
                                    borrowActivityGiveaway,bookImageName,"Available",rentPrice,userid,dateTrack);
                            if(insert==true){
                                Toast.makeText(AddingBook.this,"Book is Successfully Added", Toast.LENGTH_SHORT).show();
                                Intent addBookIntent = new Intent(AddingBook.this, Home.class);
                                addBookIntent.putExtra("userprofile",userprofile);
                                startActivity(addBookIntent);
                            }
                            else{

                                Toast.makeText(AddingBook.this,"Book is not added. Please try again.", Toast.LENGTH_SHORT).show();
                                Intent addBookIntent = new Intent(AddingBook.this, Home.class);
                                addBookIntent.putExtra("userprofile",userprofile);
                                startActivity(addBookIntent);
                            }
                        }
                    }
                }






            }
        });


    }
}