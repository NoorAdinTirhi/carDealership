package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextPwd;

    Button buttonLogin;
    Button buttonSignUp;

    CheckBox checkBox;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(LoginActivity.this, "userDB2", null,1);


    void initTables(DataBaseHelper dataBaseHelper){
        dataBaseHelper.clearTables();

        dataBaseHelper.insertCountry("Palestinian Territory", "00970");
        dataBaseHelper.insertCity("Hebron", "Palestinian Territory");
        dataBaseHelper.insertCity("Ramallah", "Palestinian Territory");
        dataBaseHelper.insertCity("Nablus", "Palestinian Territory");

        dataBaseHelper.insertCountry("Jordan", "00962");
        dataBaseHelper.insertCity("Amman", "Jordan");
        dataBaseHelper.insertCity("Aqaba", "Jordan");
        dataBaseHelper.insertCity("Irbid", "Jordan");

        dataBaseHelper.insertCountry("Lebanon", "00961");
        dataBaseHelper.insertCity("Beirut", "Lebanon");
        dataBaseHelper.insertCity("Tripoli", "Lebanon");
        dataBaseHelper.insertCity("Sidon", "Lebanon");

        dataBaseHelper.insertCountry("Saudi Arabia", "00966");
        dataBaseHelper.insertCity("McountryAdinah", "Saudi Arabia");
        dataBaseHelper.insertCity("RiycountryAdh", "Saudi Arabia");
        dataBaseHelper.insertCity("Jeddah", "Saudi Arabia");

        //Insert 1 admin and 1 normal user
        //normal user
//        dataBaseHelper.insertUser(LoginActivity.this, new User(
//                "nooradintirhi@gmail.com",
//                "NoorAdin",
//                "Tirhi",
//                "Male",
//                "Ff1!2@3#4$5%",
//                "Palestinian Territory",
//                "Ramallah",
//                "584280013"
//        ));
//        //admin user
//        dataBaseHelper.insertAdminUser(LoginActivity.this, new User(
//                        "nooradintirhi3@gmail.com",
//                        "NoorAdin",
//                        "Tirhi",
//                        "Male",
//                        "Ff1!2@3#4$5%",
//                        "Palestinian Territory",
//                        "Ramallah",
//                        "584280013"
//                ),
//                true);

        Cursor testCursor = dataBaseHelper.getAllUsers();
        while(testCursor.moveToNext()){
            Log.d("testSQL", testCursor.getString(6));
        }
    }

    String getCurrentUser(DataBaseHelper dataBaseHelper){
         Cursor cursor = dataBaseHelper.getAllUsers();
         while(cursor.moveToNext()){
             if (cursor.getInt(7) == 1)
                 return cursor.getString(0);
         }
         return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPrefManager sharedPrefManager =SharedPrefManager.getInstance(this);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextPwd = (EditText) findViewById(R.id.editTextPasswordLogin);

        buttonLogin = (Button) findViewById(R.id.button_Login);
        buttonSignUp = (Button) findViewById(R.id.button_SignUp);

        checkBox = (CheckBox) findViewById(R.id.checkBox_RemeberMe);

        initTables(dataBaseHelper);

        String currentUser = getCurrentUser(dataBaseHelper);
        if (currentUser != null){
            sharedPrefManager.writeString("loggedInEmail", currentUser);
            LoginActivity.this.startActivity( new Intent(LoginActivity.this, NavigationActivity.class));
            finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor registeredUsers = dataBaseHelper.getUserEmailPassword(editTextEmail.getText().toString());
                while(registeredUsers.moveToNext()){
                    if (registeredUsers.getString(4).equals(editTextPwd.getText().toString())){
                        sharedPrefManager.writeString("loggedInEmail", editTextEmail.getText().toString());
                        if (checkBox.isChecked()){
                            dataBaseHelper.setCurrentUser(editTextEmail.getText().toString());
                        }
                        LoginActivity.this.startActivity( new Intent(LoginActivity.this, NavigationActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                Toast.makeText(LoginActivity.this, "Email is not registered", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(switchIntent);
                finish();
            }
        });


    }
}