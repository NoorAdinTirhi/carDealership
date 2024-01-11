package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ReservationsActivity extends AppCompatActivity implements HeaderCommunicator{

    ImageView backgroundImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));
    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("RESERVES");
    }
}