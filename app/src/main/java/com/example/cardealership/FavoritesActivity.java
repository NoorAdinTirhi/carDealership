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
import java.util.Date;

public class FavoritesActivity extends AppCompatActivity  implements HeaderCommunicator, PopUpCommunicator{
    ImageView backgroundImg;

    ArrayList<Favorites> favoritessList;
    ListView favoritessListView;
    FavoritesCustomAdapter favoritesCustomAdapter;

    FragmentManager fragmentManager;
    SubmitPopUpFragment submitPopUpFragment;


    DataBaseHelper dataBaseHelper = new DataBaseHelper(FavoritesActivity.this, User.dbName, null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        fragmentManager = getSupportFragmentManager();
        submitPopUpFragment = new SubmitPopUpFragment();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        favoritessListView = (ListView) findViewById(R.id.listViewFavorites1);

        favoritessList = new ArrayList<Favorites>();
        Cursor temp = dataBaseHelper.getFavoritesByEmail(sharedPrefManager.readString("loggedInEmail","Default"));

        while (temp.moveToNext()){
            favoritessList.add(
                    new Favorites(temp.getString(0), temp.getString(1), temp.getInt(2))
            );
        }

        favoritesCustomAdapter = new FavoritesCustomAdapter(favoritessList, FavoritesActivity.this) ;

        favoritessListView.setAdapter(favoritesCustomAdapter);
    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("FAVORITES");
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
        fragmentTransaction.add(R.id.LinearLayoutPopUp, submitPopUpFragment, "submitPopUpFragment");
        fragmentTransaction.commit();

        favoritessListView.setEnabled(false);
    }

    @Override
    public void respondSubmitPopUpExit() {
        if (!fragmentManager.getFragments().contains(submitPopUpFragment))
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(submitPopUpFragment);
        fragmentTransaction.commit();

        favoritessListView.setEnabled(true);
    }
}