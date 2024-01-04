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

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{




    Button buttonBack, buttonSubmit;
    EditText editTextEmail, editTextFname, editTextLname, editTextPasswordSignUp, editTextPasswordConfirm, editTextPhone;
    Spinner spinnerGender, spinnerCountry, spinnerCity;
    User temp_user;

    String[] countries = new String[4];
    String[] phoneCodes = new String[4];
    ArrayList<String> cities = new ArrayList<String>();

    ArrayAdapter<String> countryAd;
    ArrayAdapter<String> citiesAd;
    ArrayAdapter<String> genderAd;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUpActivity.this, "userDB2", null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Cursor cursorAllCountries =  dataBaseHelper.getAllCountries();


        int i = 0;
        // Get Country Spinner Codes
        while(cursorAllCountries.moveToNext()) {
            countries[i] = cursorAllCountries.getString(0);
            phoneCodes[i] = cursorAllCountries.getString(1);
            i++;
        }

        //initialize relevant views
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmitSignUp);

        editTextEmail = (EditText) findViewById(R.id.editTextEmailSignUp);
        editTextFname = (EditText) findViewById(R.id.editTextFname);
        editTextLname = (EditText) findViewById(R.id.editTextLname);
        editTextPasswordSignUp = (EditText) findViewById(R.id.editTextPasswordSignUp);
        editTextPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        spinnerGender = (Spinner) findViewById(R.id.spinnerGenderSignUp);
        spinnerCountry = (Spinner) findViewById(R.id.spinnerCountrySignUp);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCitySignUp);

        spinnerCountry.setOnItemSelectedListener(this);

        //set spinner contents
        genderAd = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"Male", "Female"});

        genderAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGender.setAdapter(genderAd);

        countryAd = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);

        countryAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCountry.setAdapter(countryAd);

        citiesAd =  new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);

        citiesAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCity.setAdapter(citiesAd);



        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.this.startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor testCursor = dataBaseHelper.getAllUsers();
                while(testCursor.moveToNext()){
                    Log.d("testSQL",testCursor.getString(0));
                }
                if (!(patternMatches(editTextEmail.getText().toString(), "^(.+)@(\\S+)$"))){
                    Toast.makeText(SignUpActivity.this, "Bad Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(editTextFname.getText().toString(), "[a-zA-Z]{3,20}"))){
                    Toast.makeText(SignUpActivity.this, "Bad First Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(editTextLname.getText().toString(), "[a-zA-Z]{3,20}"))){
                    Toast.makeText(SignUpActivity.this, "Bad Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(editTextPasswordSignUp.getText().toString(), "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$"))){
                    Toast.makeText(SignUpActivity.this, "Make sure Password is between 8-20 characters, contains at least one number and one special character", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!editTextPasswordSignUp.getText().toString().equals(editTextPasswordConfirm.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Confirm Password and Password fields don't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(patternMatches(editTextPhone.getText().toString(), "^\\d{9}$"))){
                    Toast.makeText(SignUpActivity.this, "Make sure your phone number is correct(9 digits after the code)", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    dataBaseHelper.insertUser(SignUpActivity.this,
                            new User(editTextEmail.getText().toString(),
                                    editTextFname.getText().toString(),
                                    editTextLname.getText().toString(),
                                    spinnerGender.getSelectedItem().toString(),
                                    editTextPasswordSignUp.getText().toString(),
                                    spinnerCountry.getSelectedItem().toString(),
                                    spinnerCity.getSelectedItem().toString(),
                                    editTextPhone.getText().toString()
                            )
                    );
                    Toast.makeText(SignUpActivity.this, "SUCCESSFULLY REGISTERED USER", Toast.LENGTH_SHORT).show();
                    SignUpActivity.this.startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }catch(Exception e) {
                    Toast.makeText(SignUpActivity.this, "Email address already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        TextView textAreaPhoneCode = (TextView) findViewById(R.id.textViewAreaCode);
        textAreaPhoneCode.setText(phoneCodes[position]);

        Cursor citiesCursor  = dataBaseHelper.getAllCitiesFromCountry(countries[position]);
        int j = 0;
        cities.clear();
        while(citiesCursor.moveToNext()){
            cities.add(String.valueOf(citiesCursor.getString(0)));
        }
        citiesAd.notifyDataSetChanged();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //helper method
    public static boolean patternMatches(String str, String regexPattern) {
        return Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE)
                .matcher(str)
                .matches();
    }


}