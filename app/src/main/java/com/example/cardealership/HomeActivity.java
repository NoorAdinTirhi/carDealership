package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity implements HeaderCommunicator{

    ImageView backgroundImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(HomeActivity.this, User.dbName, null,1);

        Cursor cursor = dataBaseHelper.getAllUsers();

        while(cursor.moveToNext()){
            Log.d("testCurrentSQL", cursor.getString(7));
        }

        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));
    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("HOME");

    }
}