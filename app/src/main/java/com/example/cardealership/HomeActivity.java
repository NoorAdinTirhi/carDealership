package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements HeaderCommunicator{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

    ImageView backgroundImg;

    ArrayList<HistoryAct> actArrayList;
    ListView actListView;
    ActCustomAdapter ActCustomAdapter;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(HomeActivity.this, User.dbName, null,1);

    SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("testSharedPref", sharedPrefManager.readString("loggedInEmail", "NoUser"));

        Cursor temp = dataBaseHelper.getUserHistory(sharedPrefManager.readString("loggedInEmail", "NoUser"));

        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));


        actArrayList = new ArrayList<HistoryAct>();

        actListView = (ListView) findViewById(R.id.listViewActs);


        while (temp.moveToNext()){
            try {
                actArrayList.add(
                        new HistoryAct(temp.getString(1), dataBaseHelper.formatter.parse(temp.getString(3)), temp.getString(2))
                );
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        ActCustomAdapter = new ActCustomAdapter(actArrayList, HomeActivity.this) ;

        actListView.setAdapter(ActCustomAdapter);        while (temp.moveToNext()){
            try {
                actArrayList.add(
                        new HistoryAct(temp.getString(1), dataBaseHelper.formatter.parse(temp.getString(3)), temp.getString(2))
                );
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        ActCustomAdapter = new ActCustomAdapter(actArrayList, HomeActivity.this) ;

        actListView.setAdapter(ActCustomAdapter);



    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("HOME");

    }
}