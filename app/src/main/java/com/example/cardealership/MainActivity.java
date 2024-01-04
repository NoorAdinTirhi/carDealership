package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectButton = (Button) findViewById(R.id.button_Connect);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://658582eb022766bcb8c8c86e.mockapi.io/api/mock/rest-apis/encs5150/car-types");
            }
        });





    }

    public void setButtonText(String text) {
        connectButton.setText(text);
    }
    public void errorToast(){
        Toast.makeText(MainActivity.this, "FAILED TO CONNECT", Toast.LENGTH_SHORT).show();
    }
    public void moveToLogin(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(intent);
        finish();
    }

//    public void fillCars(ArrayList<Car> cars){
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.testList);
//
//        for (int i = 0; i< cars.size(); i++){
//            TextView li = new TextView(this);
//            li.setText(cars.get(i).toString());
//            linearLayout.addView(li);
//        }
//    }



}