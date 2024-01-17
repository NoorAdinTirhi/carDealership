package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class SpecialOffersActivity extends AppCompatActivity implements HeaderCommunicator, PopUpCommunicator{

    ImageView backgroundImg;

    ListView listViewOffers;
    SubmitPopUpFragment submitPopUpFragment;

    static ArrayList<SpecialOffer> specialOfferArrayList;

    SpecialOfferCustomAdapter specialOfferCustomAdapter;

    FragmentManager fragmentManager;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(SpecialOffersActivity.this, User.dbName, null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_offers);
        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        fragmentManager = getSupportFragmentManager();
        submitPopUpFragment = new SubmitPopUpFragment();

        listViewOffers = (ListView) findViewById(R.id.listViewOffers);

        Cursor temp = dataBaseHelper.getSpecialOfferCar();
        specialOfferArrayList = new ArrayList<SpecialOffer>();

        while(temp.moveToNext())
            specialOfferArrayList.add(new SpecialOffer(temp.getInt(1),temp.getString(0), temp.getInt(2),temp.getInt(3) ));

        specialOfferCustomAdapter = new SpecialOfferCustomAdapter(specialOfferArrayList,this);

        listViewOffers.setAdapter(specialOfferCustomAdapter);

    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("SPECIAL OFFERS");
    }

    @Override
    public void respondPopUpExit() {

    }

    @Override
    public void respondPopUpLaunch(int ID, String type, int price) {

    }

    @Override
    public void respondOpenFragment() {

    }

    @Override
    public void addToFavourites(String email, int carID) {

    }

    @Override
    public void respondSubmitPopUpLaucnh(int ID, String type, int price) {
        if (fragmentManager.getFragments().contains(submitPopUpFragment))
            return;
        Car.currentCar.setType(type);
        Car.currentCar.setId(ID);
        Car.currentCar.setPrice(price);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.LayoutPopUp, submitPopUpFragment, "submitPopUpFragment");
        fragmentTransaction.commit();

        listViewOffers.setEnabled(false);
    }

    @Override
    public void respondSubmitPopUpExit() {
        if (!fragmentManager.getFragments().contains(submitPopUpFragment))
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(submitPopUpFragment);
        fragmentTransaction.commit();

        listViewOffers.setEnabled(true);
    }
}