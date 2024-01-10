package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(NavigationActivity.this, User.dbName, null,1);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        Button buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity.this.startActivity( new Intent(NavigationActivity.this, LoginActivity.class));
                //No remembered email
                sharedPrefManager.writeString("RememberedEmail", "NoUser");
                //No user is currently logged in
                sharedPrefManager.writeString("loggedInEmail","NoUser");
                NavigationActivity.this.finish();
            }
        });

        //initialize the fragment
        NavigationMenuFragment fragmentHome = (NavigationMenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentNavigation);

    }
}