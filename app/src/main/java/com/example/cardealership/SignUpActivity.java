package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity{

    Button buttonBack, buttonSubmit;

    Cursor matchEmailCursor;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUpActivity.this, User.dbName, null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialize relevant views
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmitSignUp);

        InputFieldsFragment fieldsFragment = (InputFieldsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentInputFields);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.this.startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(patternMatches(fieldsFragment.getEmail(), "^(.+)@(\\S+)$"))){
                    Toast.makeText(SignUpActivity.this, "BAD EMAIL", Toast.LENGTH_SHORT).show();
                    return;
                }

                matchEmailCursor = dataBaseHelper.getUserEmailPassword(fieldsFragment.getEmail());
                while(matchEmailCursor.moveToNext()){
                    Toast.makeText(SignUpActivity.this, "EMAIL ALREADY REGISTERED", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(patternMatches(fieldsFragment.getFname(), "[a-zA-Z]{3,20}"))){
                    Toast.makeText(SignUpActivity.this, "BAD FIRST NAME", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(fieldsFragment.getLname(), "[a-zA-Z]{3,20}"))){
                    Toast.makeText(SignUpActivity.this, "BAD LAST NAME", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(fieldsFragment.getPassword(), "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$"))){
                    Toast.makeText(SignUpActivity.this, "Make sure Password is between 8-20 characters, contains at least one number and one special character", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!fieldsFragment.getPassword().equals(fieldsFragment.getConfirmPassword())){
                    Toast.makeText(SignUpActivity.this, "Confirm Password and Password fields don't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(patternMatches(fieldsFragment.getPhone(), "^\\d{9}$"))){
                    Toast.makeText(SignUpActivity.this, "Make sure your phone number is correct(9 digits after the code)", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataBaseHelper.insertUser(SignUpActivity.this,
                    new User(fieldsFragment.getEmail(),
                        fieldsFragment.getFname(),
                        fieldsFragment.getLname(),
                        fieldsFragment.getGender(),
                        fieldsFragment.getPassword(),
                        fieldsFragment.getCountry(),
                        fieldsFragment.getCity(),
                        fieldsFragment.getPhone()
                    )
                );

                Toast.makeText(SignUpActivity.this, "SUCCESSFULLY REGISTERED USER", Toast.LENGTH_SHORT).show();
                SignUpActivity.this.startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    //helper method
    public static boolean patternMatches(String str, String regexPattern) {
        return Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE)
                .matcher(str)
                .matches();
    }


}