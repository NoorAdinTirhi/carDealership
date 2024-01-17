package com.example.cardealership;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarMenuActivity extends AppCompatActivity implements HeaderCommunicator, PopUpCommunicator, AdapterView.OnItemSelectedListener{

    ImageView backgroundImg;

    Spinner spinnerFilter;
    ArrayAdapter<String> filterAd;

    EditText editTextSearchField;
    ListView listViewCars;
    PopUpFragment popUpFragment;
    SubmitPopUpFragment submitPopUpFragment;

    static ArrayList<Car> tempCarList;

    CarListCustomAdapter carListCustomAdapter;

    FragmentManager fragmentManager;


    DataBaseHelper dataBaseHelper = new DataBaseHelper(CarMenuActivity.this, User.dbName, null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_menu);
        backgroundImg = (ImageView) findViewById(R.id.imageBackground);
        backgroundImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

        tempCarList = (ArrayList<Car>) Car.carList.clone();

        fragmentManager = getSupportFragmentManager();
        popUpFragment = new PopUpFragment();
        submitPopUpFragment = new SubmitPopUpFragment();

        spinnerFilter = (Spinner) findViewById(R.id.spinnerFilter);
        editTextSearchField = (EditText) findViewById(R.id.editTextSearchField);

        listViewCars = (ListView) findViewById(R.id.listViewCars);

        carListCustomAdapter = new CarListCustomAdapter(tempCarList, this);

        listViewCars.setAdapter(carListCustomAdapter);

        listViewCars.setOnItemSelectedListener(this);



        filterAd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"PRICE", "NAME", "ID"});
        filterAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAd);
        spinnerFilter.setOnItemSelectedListener(this);

        popUpFragment = new PopUpFragment();



        editTextSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String StringSearchField =  editTextSearchField.getText().toString();
                String StringFilter = spinnerFilter.getSelectedItem().toString();

                if (StringSearchField.equals("")) {
                    tempCarList.clear();
                    tempCarList.addAll(Car.carList);
                    carListCustomAdapter.notifyDataSetChanged();
                    return;
                }

                tempCarList.clear();
                if (StringFilter.equals("PRICE")){
                    try {
                        int priceCenter = Integer.parseInt(StringSearchField);

                        for (Car car : Car.carList) {
                            if ((car.getPrice() >= (.85 * priceCenter)) & (car.getPrice() <= (1.15 * priceCenter)))
                                tempCarList.add(car);
                        }
                        carListCustomAdapter.notifyDataSetChanged();
                        Log.d("Nan exception", tempCarList.toString());
                    } catch (Exception e) {
                        Log.d("Nan exception", e.toString());
                    }

                }else if (StringFilter.equals("NAME")){
                    Pattern pattern = Pattern.compile( StringSearchField, Pattern.CASE_INSENSITIVE);

                    for (Car car : Car.carList) {
                        Matcher matcher = pattern.matcher(car.getType());
                        if (matcher.find())
                            tempCarList.add(car);
                    }

                }else if (StringFilter.equals("ID")){
                    try {
                        int id = Integer.parseInt(StringSearchField);

                        for (Car car : Car.carList) {
                            if (car.getId() == id) {
                                tempCarList.add(car);
                                break;
                            }
                        }
                        carListCustomAdapter.notifyDataSetChanged();
                        Log.d("Nan exception", tempCarList.toString());
                    } catch (Exception e) {
                        Log.d("Nan exception", e.toString());
                    }
                }
                carListCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }

    @Override
    public void respondTitle(){
        HeaderLabelFragment titleFragment = (HeaderLabelFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        titleFragment.setTitle("CAR MENU");
    }



    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if (arg0.getId() == R.id.listViewCars){
            Log.d("adapterParent", String.valueOf(R.id.listViewCars));
        }else if (arg0.getId() == R.id.spinnerFilter){
            Log.d("adapterParent", String.valueOf(R.id.spinnerFilter));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void respondPopUpLaunch(int ID, String type, int price){
        if (fragmentManager.getFragments().contains(popUpFragment))
            return;
        Car.currentCar.setType(type);
        Car.currentCar.setId(ID);
        Car.currentCar.setPrice(price);


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.LayoutPopUp, popUpFragment, "popUpFragment");
        fragmentTransaction.commit();

        listViewCars.setEnabled(false);

    }

    @Override
    public void respondPopUpExit(){
        if (!fragmentManager.getFragments().contains(popUpFragment))
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(popUpFragment);
        fragmentTransaction.commit();

        listViewCars.setEnabled(true);
    }

    @Override
    public void respondOpenFragment(){
    }

    @Override
    public void addToFavourites(String email, int carID){
        dataBaseHelper.addToFavorites( this,email, carID);
    }

    @Override
    public void respondSubmitPopUpLaucnh(int ID, String type, int price){
        if (fragmentManager.getFragments().contains(submitPopUpFragment))
            return;
        Car.currentCar.setType(type);
        Car.currentCar.setId(ID);
        Car.currentCar.setPrice(price);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.LayoutPopUp, submitPopUpFragment, "submitPopUpFragment");
        fragmentTransaction.commit();

        listViewCars.setEnabled(false);
    }

    @Override
    public void respondSubmitPopUpExit(){
        if (!fragmentManager.getFragments().contains(submitPopUpFragment))
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(submitPopUpFragment);
        fragmentTransaction.commit();

        listViewCars.setEnabled(true);
    }


}