package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SpecialOffersActivity extends AppCompatActivity implements HeaderCommunicator{

    ImageView backgroundImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_offers);

        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));
    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("SPECIAL OFFERS");
    }
}