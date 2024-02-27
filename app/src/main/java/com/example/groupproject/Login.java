//package com.example.groupproject;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import entities.UserProfile;
//
//// Done by Steven Ning-300324107
////
//public class Login extends AppCompatActivity {
//
//    EditText username,password;
//    Button btnlogin, btnCreateAccount;
//    DBHelper DB;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        setTitle(R.string.btnLogin);
//        username = (EditText) findViewById(R.id.txtUserLog);
//        password = (EditText) findViewById(R.id.txtPass);
//        btnlogin = (Button) findViewById(R.id.btnLog);
//        btnCreateAccount = (Button) findViewById(R.id.btnCreateAcc);
//        DB = new DBHelper(this);
//
//
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String user = username.getText().toString();
//                String pass = password.getText().toString();
//
//
//                if(user.equals("") || pass.equals("")){
//                    //checks if any fields are blank in the password/username field, if empty asks for the prompt to be filled
//
//                    Toast.makeText(Login.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    UserProfile userProfile = DB.login(user,pass);
//                    if(userProfile == null){
//                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                        builder.setTitle(R.string.error);
//                        builder.setMessage(R.string.invalid);
//                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//                        builder.show();
//                    }
//                    else {
//                        Toast.makeText(Login.this,"Sign in successful", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), Home.class);
//                        intent.putExtra("userprofile",userProfile);
//                        startActivity(intent);
//                    }
//
//                }
//
//            }
//        });
//        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Login.this, Register.class));
//            }
//        });
//
//
//
//    }
//
//
//
//}

package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entities.UserProfile;

// Done by Steven Ning-300324107
//

/*
public class Login extends AppCompatActivity {

    EditText username, password;
    Button btnlogin, btnCreateAccount;
    DBHelper DB;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.txtUserLog);
        password = (EditText) findViewById(R.id.txtPass);
        btnlogin = (Button) findViewById(R.id.btnLog);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAcc);
        DB = new DBHelper(this);



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();


                if(user.equals("") || pass.equals("")){
                    //checks if any fields are blank in the password/username field, if empty asks for the prompt to be filled

                    Toast.makeText(Login.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass){
                        // checks if the username and password match in the database for the login, if correct, logs the user in and sends to the home activity/page
                        Toast.makeText(Login.this,"Sign in successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Home.class));
                    }else{
                        // if returns a false value, will tell the user the password/login do no match
                        Toast.makeText(Login.this,"Invalid info", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        if (session.loggedin()) {
            startActivity(new Intent(Login.this, Home.class));
            finish();
        }

    }

}
*/

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText username, password;
    Button btnlogin, btnCreateAccount;
    DBHelper DB;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DB = new DBHelper(this);
        session = new Session(this);
        btnlogin = (Button) findViewById(R.id.btnLog);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAcc);
        username = (EditText) findViewById(R.id.txtUserLog);
        password = (EditText) findViewById(R.id.txtPass);
        btnlogin.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnLog:
                btnlogin();
                break;
            case R.id.btnCreateAcc:

                startActivity(new Intent(Login.this,Register.class));
                break;
            default:
        }
    }

    private void btnlogin() {

        String user1 =  username.getText().toString();
        String pass1 = password.getText().toString();
        UserProfile userProfile = DB.login(user1,pass1);
        if(userProfile != null){
            session.setLoggedin(true);
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("userprofile",userProfile);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"Invalid info",Toast.LENGTH_SHORT).show();
        }
//        if(DB.checkusernamepassword(user1,pass1)){
//            session.setLoggedin(true);
//            startActivity(new Intent(Login.this,Home.class));
//            finish();
//        }else{
//            Toast.makeText(getApplicationContext(),"Invalid info",Toast.LENGTH_SHORT).show();
//        }
    }

}
