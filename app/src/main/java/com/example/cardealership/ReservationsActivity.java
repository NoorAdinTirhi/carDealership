package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ReservationsActivity extends AppCompatActivity implements HeaderCommunicator{

    ImageView backgroundImg;

    ArrayList<Reserves> reservesList;
    ListView reservesListView;
    ReservationCustomAdapter reserveCustomAdapter;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(ReservationsActivity.this, User.dbName, null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        reservesListView = (ListView) findViewById(R.id.listViewReserves1);

        reservesList = new ArrayList<Reserves>();
        Cursor temp = dataBaseHelper.getReservations();

        while (temp.moveToNext()){
            reservesList.add(
                    new Reserves(temp.getString(0) + temp.getString(1), temp.getString(2), temp.getString(3), temp.getString(4))
            );
        }

        reserveCustomAdapter = new ReservationCustomAdapter(reservesList, ReservationsActivity.this) ;

        reservesListView.setAdapter(reserveCustomAdapter);


    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("RESERVES");
    }
}