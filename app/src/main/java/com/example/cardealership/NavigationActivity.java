package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(NavigationActivity.this, "userDB2", null,1);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        Button buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity.this.startActivity( new Intent(NavigationActivity.this, LoginActivity.class));
                dataBaseHelper.setCurrentUser("-----");
                sharedPrefManager.writeString("loggedInEmail","NoUser");
                NavigationActivity.this.finish();
            }
        });

        //first we will set the text of our fragments
        NavigationMenuFragment fragmentHome = (NavigationMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHome);
        NavigationMenuFragment fragmentCarMenu = (NavigationMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCarMenu);
        NavigationMenuFragment fragmentFavorites = (NavigationMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentFavourites);
        NavigationMenuFragment fragmentSpecialOffers = (NavigationMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentSpecialOffers);
        NavigationMenuFragment fragmentProfile = (NavigationMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentProfile);
        NavigationMenuFragment fragmentCallorFindUs = (NavigationMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentCallorFindUs);


//        fragmentHome.setTexts("HOME", "View Your History");
//        fragmentCarMenu.setTexts("CAR MENU", "View All Car Types and Relevant Information");
//        fragmentFavorites.setTexts("YOUR FAVORITES", "View Your Favorites");
//        fragmentSpecialOffers.setTexts("SPECIAL OFFERS", "View Special Offers");
//        fragmentProfile.setTexts("PROFILE","View and Edit Profile Settings");
//        fragmentCallorFindUs.setTexts("CALL OR FIND US", "Want To Call Us? Click Here");


//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.navigationMenuLayout, fragmentHome, "fragmentHome");
//        fragmentTransaction.add(R.id.navigationMenuLayout, fragmentCarMenu, "fragmentCarMenu");
//        fragmentTransaction.add(R.id.navigationMenuLayout, fragmentFavorites, "fragmentFavorites");
//        fragmentTransaction.add(R.id.navigationMenuLayout, fragmentSpecialOffers, "fragmentSpecialOffers");
//        fragmentTransaction.add(R.id.navigationMenuLayout, fragmentProfile, "fragmentProfile");
//        fragmentTransaction.add(R.id.navigationMenuLayout, fragmentCallorFindUs, "fragmentCallorFindUs");
//
//        fragmentTransaction.commit();


    }
}