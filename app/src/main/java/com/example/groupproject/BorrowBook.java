package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import entities.UserProfile;

public class BorrowBook extends AppCompatActivity {
    UserProfile userprofile;
    //DBHelper databaseHelper;
    String strBorrowActivity="";
    //StringBuilder strTest = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);

        //databaseHelper = new DBHelper(this);
        //TextView txtTestRdBtn = findViewById(R.id.txtRdBtn);
        EditText postalCode = findViewById(R.id.inputPostalCode);
        RadioGroup rdBtnGrpBorrowBook = findViewById(R.id.rdBtnGrpBorrowBook);
        final RadioButton rdbtnShare = findViewById(R.id.rdbtnShare);
        final RadioButton rdBtnRent = findViewById(R.id.rdBtnRent);
        final RadioButton rdBtnGiveAway = findViewById(R.id.rdBtnGiveAway);
        int idBorrowActivity = rdBtnGrpBorrowBook.getCheckedRadioButtonId();
        RadioButton rdBtnBorrowActivity = findViewById(idBorrowActivity);
        Button btnFind = (Button) findViewById(R.id.btnFindNearByBooks);
        Intent intent = getIntent();
        userprofile = (UserProfile) intent.getSerializableExtra("userprofile");
        //Button btnTestRdBtn = findViewById(R.id.btnTestRdBtn);
        /*btnTestRdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTestRdBtn.setText("You selected "+ rdBtnBorrowActivity.getText().toString());
            }
        });*/

       /* if(rdbtnShare.isChecked()){
            strBorrowActivity="Share";
            Toast.makeText(this, "Selected button is "+ strBorrowActivity, Toast.LENGTH_LONG).show();

        }else if(rdBtnRent.isChecked()){
            strBorrowActivity="Rent";
            Toast.makeText(this, "Selected button is "+ strBorrowActivity, Toast.LENGTH_LONG).show();
        }
        else if(rdBtnGiveAway.isChecked()){
            strBorrowActivity="Give Away";
            Toast.makeText(this, "Selected button is "+ strBorrowActivity, Toast.LENGTH_LONG).show();
        }*/
        /*strTest.append(" PostalCode : " + postalCode.getText().toString());
        strTest.append(" BorrowActivity : " + strBorrowActivity);
        txtTestRdBtn.setText(strTest);*/
        /*Cursor data = databaseHelper.viewBooksAvailability(postalCode.getText().toString(), strBorrowActivity);
        if (data.getCount() > 0) {
            while (data.moveToNext()) {

            }
        } else {
            Toast.makeText(BorrowBook.this,
                    "Sorry, no neraby books available at this address for the selected option. Please try the other options or different postal code", Toast.LENGTH_SHORT).show();
        }*/

                //TextView txtOutput = findViewById(R.id.txtBookDb);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rdbtnShare.isChecked()){
                    strBorrowActivity="Share";
                    //Toast.makeText(BorrowBook.this, "Selected button is "+ strBorrowActivity, Toast.LENGTH_LONG).show();

                }else if(rdBtnRent.isChecked()){
                    strBorrowActivity="Rent";
                    //Toast.makeText(BorrowBook.this, "Selected button is "+ strBorrowActivity, Toast.LENGTH_LONG).show();
                }
                else if(rdBtnGiveAway.isChecked()){
                    strBorrowActivity="Give Away";
                    //Toast.makeText(BorrowBook.this, "Selected button is "+ strBorrowActivity, Toast.LENGTH_LONG).show();
                }
                /*
                if(rdBtnBorrowActivity!= null){

                    strBorrowActivity = rdBtnBorrowActivity.getText().toString();
                    Toast.makeText(BorrowBook.this,"Borrow Activity is"+ strBorrowActivity, Toast.LENGTH_LONG).show();
                }*/
                /*else {
                    Toast.makeText(BorrowBook.this,"Please select an option from Share/Rent/Give Away", Toast.LENGTH_SHORT).show();
                }*/
                //Intent intent = new Intent(BorrowBook.this, ListBooks.class);
                Intent intent = new Intent(getApplicationContext(), ListBooks.class);
                intent.putExtra("postalData", postalCode.getText().toString());
                intent.putExtra("borrowActivityData", strBorrowActivity);
                intent.putExtra("userprofile",userprofile);
                startActivity(intent);
                //startActivity(new Intent(BorrowBook.this, NearByBooks.class));
                //Cursor c = databaseHelper.viewBookOwner(postalCode.getText().toString());
                //StringBuilder str = new StringBuilder();
            }
        });
    }
}