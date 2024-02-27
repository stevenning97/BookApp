package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import entities.UserProfile;

public class UpdateBook extends AppCompatActivity {

    int userid;
    UserProfile userprofile;
    ImageView imageView;


    EditText txtBookTitleUpdate,txtAuthorUpdate,txtPublisherUpdate,txtPulicationYearUpdate,txtRentUpdate,txtBookImageUpdate,txtStatusUpdate;
    CheckBox chkRentUpdate, chkShareUpdate, chkGiveawayUpdate;
    Button btnUpdate, btnCancelUpdate;

    float rentPrice;
    String borrowActivityRent = "";
    String borrowActivityShare = "";
    String borrowActivityGiveaway = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        setTitle("Update Book Information");

        DBHelper dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        String bookID = intent.getStringExtra("bookID");
        userid = userprofile.getUserId();

        txtStatusUpdate = (EditText) findViewById(R.id.txtStatusUpdate);
        txtBookTitleUpdate = (EditText) findViewById(R.id.txtBookTitleUpdate);
        txtAuthorUpdate = (EditText) findViewById(R.id.txtAuthorUpdate);
        txtPublisherUpdate = (EditText) findViewById(R.id.txtPublisherUpdate);
        txtPulicationYearUpdate = (EditText) findViewById(R.id.txtPublicationYearUpdate);
        txtRentUpdate = (EditText) findViewById(R.id.txtRentPriceUpdate);
        txtBookImageUpdate = (EditText) findViewById(R.id.txtBookImageUpdate);
        chkShareUpdate = (CheckBox) findViewById(R.id.chkShareUpdate);
        chkRentUpdate = (CheckBox) findViewById(R.id.chkRentUpdate);
        chkGiveawayUpdate = (CheckBox) findViewById(R.id.chkGiveawayUpdate);
        btnCancelUpdate = (Button) findViewById(R.id.btnCancelUpdate);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnCancelIntent = new Intent(UpdateBook.this, Home.class);
                btnCancelIntent.putExtra("userprofile", userprofile);
                startActivity(btnCancelIntent);

            }
        });
        chkRentUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtRentUpdate.setEnabled(true);
            }
        });

        Cursor c = dbHelper.getSingleBookInfo(Integer.valueOf(bookID));
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                txtBookTitleUpdate.setText(c.getString(1));
                txtAuthorUpdate.setText(c.getString(2));
                txtPublisherUpdate.setText(c.getString(3));
                txtPulicationYearUpdate.setText(c.getString(4));
                txtStatusUpdate.setText(c.getString(9));
                txtRentUpdate.setText(c.getString(10));
                txtBookImageUpdate.setText(c.getString(8));
                if (c.getString(5).equals("Yes")) {
                    chkShareUpdate.setChecked(true);
                }
                if (c.getString(6).equals("Yes")) {
                    chkRentUpdate.setChecked(true);
                }
                if (c.getString(7).equals("Yes")) {
                    chkGiveawayUpdate.setChecked(true);
                }
            }
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {

            boolean isUpdated;

            @Override
            public void onClick(View view) {


                String bookTitle = txtBookTitleUpdate.getText().toString();
                String author = txtAuthorUpdate.getText().toString();
                String publisher = txtPublisherUpdate.getText().toString();
                String publicationYear = txtPulicationYearUpdate.getText().toString();
                String bookImageName = txtBookImageUpdate.getText().toString();
                String booStatus = txtStatusUpdate.getText().toString();
                float rentPrice;

                if ((txtRentUpdate.getText().toString() != null) && (txtRentUpdate.getText().toString().length()) > 0) {
                    rentPrice = Float.parseFloat(txtRentUpdate.getText().toString());
                } else {
                    rentPrice = 0;
                }

                if (chkShareUpdate.isChecked()) {
                    borrowActivityShare = "Yes";
                } else {
                    borrowActivityShare = "No";
                }
                if (chkRentUpdate.isChecked()) {
                    borrowActivityRent = "Yes";

                } else {
                    borrowActivityRent = "No";
                }
                if (chkGiveawayUpdate.isChecked()) {
                    borrowActivityGiveaway = "Yes";

                } else {
                    borrowActivityGiveaway = "No";
                }

                if (bookTitle.equals("") || author.equals("") || bookImageName.equals("")) {
                    Toast.makeText(UpdateBook.this, "Please enter details to all the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (!chkShareUpdate.isChecked() && !chkRentUpdate.isChecked() && !chkGiveawayUpdate.isChecked()) {
                        Toast.makeText(UpdateBook.this, "Please Select at least one borrow activity ", Toast.LENGTH_SHORT).show();
                    } else {
                        if (chkRentUpdate.isChecked() && rentPrice == 0) {
                            Toast.makeText(UpdateBook.this, "Please Enter the rent amount", Toast.LENGTH_SHORT).show();
                        } else {

                            isUpdated = dbHelper.updateBookData(Integer.valueOf(bookID), bookTitle, author, publisher, publicationYear, borrowActivityShare,
                                    borrowActivityRent, borrowActivityGiveaway, bookImageName, booStatus, rentPrice);
                            if (isUpdated)
                            {
                                Toast.makeText(UpdateBook.this, "record is updated", Toast.LENGTH_SHORT).show();
                                Intent addBookIntent = new Intent(UpdateBook.this, Home.class);
                                addBookIntent.putExtra("userprofile",userprofile);
                                startActivity(addBookIntent);
                            }

                            else
                            {
                                Toast.makeText(UpdateBook.this, "record is not updated. Please try again", Toast.LENGTH_SHORT).show();
                                Intent addBookIntent = new Intent(UpdateBook.this, Home.class);
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