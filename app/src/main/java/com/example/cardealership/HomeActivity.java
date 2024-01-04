package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(HomeActivity.this, "userDB2", null,1);

        Cursor cursor = dataBaseHelper.getAllUsers();

        while(cursor.moveToNext()){
            Log.d("testCurrentSQL", cursor.getString(7));
        }
    }
}