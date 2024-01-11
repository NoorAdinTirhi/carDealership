package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AddAdminActivity extends AppCompatActivity implements HeaderCommunicator, ConfirmationCommunicator {

    ImageView backgroundImg;
    Button buttonSubmit;

    InputFieldsFragment fieldsFragment;
    ConfirmationFragment confirmFragment;

    FragmentManager fragmentManager;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(AddAdminActivity.this, User.dbName, null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        fragmentManager = getSupportFragmentManager();
        confirmFragment = new ConfirmationFragment();

        backgroundImg = (ImageView) findViewById(R.id.imageView4);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmitSignUp);
        fieldsFragment = (InputFieldsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentInputFields);

        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(patternMatches(fieldsFragment.getEmail(), "^(.+)@(\\S+)$"))){
                    Toast.makeText(AddAdminActivity.this, "BAD EMAIL", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor matchEmailCursor;

                matchEmailCursor = dataBaseHelper.getUserEmailPassword(fieldsFragment.getEmail());
                while(matchEmailCursor.moveToNext()){
                    Toast.makeText(AddAdminActivity.this, "EMAIL ALREADY REGISTERED", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(patternMatches(fieldsFragment.getFname(), "[a-zA-Z]{3,20}"))){
                    Toast.makeText(AddAdminActivity.this, "BAD FIRST NAME", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(fieldsFragment.getLname(), "[a-zA-Z]{3,20}"))){
                    Toast.makeText(AddAdminActivity.this, "BAD LAST NAME", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(patternMatches(fieldsFragment.getPassword(), "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$"))){
                    Toast.makeText(AddAdminActivity.this, "Make sure Password is between 8-20 characters, contains at least one number and one special character", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!fieldsFragment.getPassword().equals(fieldsFragment.getConfirmPassword())){
                    Toast.makeText(AddAdminActivity.this, "Confirm Password and Password fields don't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(patternMatches(fieldsFragment.getPhone(), "^\\d{9}$"))){
                    Toast.makeText(AddAdminActivity.this, "Make sure your phone number is correct(9 digits after the code)", Toast.LENGTH_SHORT).show();
                    return;
                }

                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.layoutConfirmation, confirmFragment, "confirmFrag");
                fragmentTransaction.commit();


            }
        });
    }
    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("ADD ADMIN");
    }

    @Override
    public void respondYes(){
        dataBaseHelper.insertAdminUser(AddAdminActivity.this,
                        new User(fieldsFragment.getEmail(),
                                fieldsFragment.getFname(),
                                fieldsFragment.getLname(),
                                fieldsFragment.getGender(),
                                fieldsFragment.getPassword(),
                                fieldsFragment.getCountry(),
                                fieldsFragment.getCity(),
                                fieldsFragment.getPhone()
                        ),
                true
        );

                Toast.makeText(AddAdminActivity.this, "SUCCESSFULLY REGISTERED ADMIN USER", Toast.LENGTH_SHORT).show();
                AddAdminActivity.this.startActivity(new Intent(AddAdminActivity.this, AdminNavigationMenuActivity.class));
                finish();
    }

    @Override
    public void respondCancel(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(confirmFragment);
        fragmentTransaction.commit();
    }

    public static boolean patternMatches(String str, String regexPattern) {
        return Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE)
                .matcher(str)
                .matches();
    }
}