package com.example.cardealership;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class ConnectionAsyncTask extends AsyncTask<String, String,
        String> {
    Activity activity;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        ((MainActivity) activity).setButtonText("connecting");
        super.onPreExecute();
//        ((MainActivity) activity).setProgress(true);
    }
    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }
    @Override
    protected void onPostExecute(String s) {
        try {
            super.onPostExecute(s);
            Log.d("test", s);
            ((MainActivity) activity).setButtonText("connected");
            Car.carList = CarJsonParser.getObjectFromJson(s);
            Log.d("carListTest", Car.carList.toString());
            ((MainActivity) activity).moveToLogin();
//            ((MainActivity) activity).fillCars(cars);
        }catch(Exception e){
            ((MainActivity) activity).setButtonText("connect");
            ((MainActivity)activity).errorToast();
        }


    }
}