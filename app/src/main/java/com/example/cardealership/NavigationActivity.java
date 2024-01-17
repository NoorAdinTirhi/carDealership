package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavigationActivity extends AppCompatActivity {

    private static final String MY_CHANNEL_ID = "my_channel_1";
    private static final String MY_CHANNEL_NAME = "MyChannel1";

    private static int NOTIFICATION_ID = 123;

    private static final String TOAST_TEXT = "This my toast message";


    private void createNotificationChannel() {
        int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID, MY_CHANNEL_NAME, importance);
        android.app.NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification(String Title, String Body) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Title)
                .setContentText(Body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
        NOTIFICATION_ID = NOTIFICATION_ID + 1;
    }

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

        Cursor temp  = dataBaseHelper.getSpecialOffersInfo();
        int i = 0;
        if (temp.getCount() > 3){
            createNotification("SPECIAL OFFER", "Check The Special Offers Menu To See Our Many Deals" );

        }else {
            while (temp.moveToNext()) {

                createNotification("SPECIAL OFFER", temp.getString(2) + "%OFF" +
                        " for" + temp.getString(0) + " " + "#" + temp.getString(1));
            }
        }

    }
}