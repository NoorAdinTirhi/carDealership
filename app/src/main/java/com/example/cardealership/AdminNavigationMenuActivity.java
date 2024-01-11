package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminNavigationMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_navigation_menu);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        Button buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminNavigationMenuActivity.this, LoginActivity.class));
                //No remembered email
                sharedPrefManager.writeString("RememberedEmail", "NoUser");
                //No user is currently logged in
                sharedPrefManager.writeString("loggedInEmail","NoUser");

                AdminNavigationMenuActivity.this.finish();
            }
        });
    }
}