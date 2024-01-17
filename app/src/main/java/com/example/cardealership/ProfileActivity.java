package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity implements HeaderCommunicator, InputFieldsCommunicator{

    ImageView backgroundImg;

    Button buttonSubmit;

    InputFieldsFragment fieldsFragment;
    Cursor matchEmailCursor;




    DataBaseHelper dataBaseHelper = new DataBaseHelper(ProfileActivity.this, User.dbName, null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        buttonSubmit = (Button) findViewById(R.id.buttonSubmitSignUp);

        fieldsFragment = (InputFieldsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentInputFields);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager sharedPrefManager =SharedPrefManager.getInstance(ProfileActivity.this);

                if (!(patternMatches(fieldsFragment.getEmail(), "^(.+)@(\\S+)$"))){
                    Toast.makeText(ProfileActivity.this, "BAD EMAIL", Toast.LENGTH_SHORT).show();
                    return;
                }

                matchEmailCursor = dataBaseHelper.getUserEmailPassword(fieldsFragment.getEmail());
                while(matchEmailCursor.moveToNext()){
                    if (!matchEmailCursor.getString(0).equals(sharedPrefManager.readString("loggedInEmail","Default"))) {
                        Toast.makeText(ProfileActivity.this, "EMAIL ALREADY REGISTERED", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(!(patternMatches(fieldsFragment.getFname(), "[a-zA-Z]{3,20}"))){
                    Toast.makeText(ProfileActivity.this, "BAD FIRST NAME", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(fieldsFragment.getLname(), "[a-zA-Z]{3,20}"))){
                    Toast.makeText(ProfileActivity.this, "BAD LAST NAME", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(fieldsFragment.getPassword(), "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$"))){
                    Toast.makeText(ProfileActivity.this, "Make sure Password is between 8-20 characters, contains at least one number and one special character", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!fieldsFragment.getPassword().equals(fieldsFragment.getConfirmPassword())){
                    Toast.makeText(ProfileActivity.this, "Confirm Password and Password fields don't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(patternMatches(fieldsFragment.getPhone(), "^\\d{9}$"))){
                    Toast.makeText(ProfileActivity.this, "Make sure your phone number is correct(9 digits after the code)", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataBaseHelper.updateUser(ProfileActivity.this,
                        new User(sharedPrefManager.readString("loggedInEmail","Default"),
                                fieldsFragment.getFname(),
                                fieldsFragment.getLname(),
                                fieldsFragment.getGender(),
                                fieldsFragment.getPassword(),
                                fieldsFragment.getCountry(),
                                fieldsFragment.getCity(),
                                fieldsFragment.getPhone()
                        )
                );

                Toast.makeText(ProfileActivity.this, "SUCCESSFULLY ALTERED INFORMATION", Toast.LENGTH_SHORT).show();
                dataBaseHelper.insertHistoryAct(new HistoryAct(sharedPrefManager.readString("loggedInEmail","Default"), new Date(), "Updated Information"));
                finish();
            }
        });
    }



    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("PROFILE");
    }
    @Override
    public void respond(){
        SharedPrefManager sharedPrefManager =SharedPrefManager.getInstance(this);
        Cursor temp = dataBaseHelper.getUserEmailPassword(sharedPrefManager.readString("loggedInEmail", "Default"));
        while(temp.moveToNext()){
            Cursor countryTemp = dataBaseHelper.getCountryByCity(temp.getString(5));
            while(countryTemp.moveToNext()){
                fieldsFragment.seTexts(temp.getString(0), temp.getString(1),
                        temp.getString(2), temp.getString(4), temp.getString(7),
                        temp.getString(3), countryTemp.getString(1), temp.getString(5));

            }
        }
    }

    public static boolean patternMatches(String str, String regexPattern) {
        return Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE)
                .matcher(str)
                .matches();
    }
}