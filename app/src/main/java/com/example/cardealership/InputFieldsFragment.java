package com.example.cardealership;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputFieldsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class InputFieldsFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFieldsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputFieldsFragment newInstance(String param1, String param2) {
        InputFieldsFragment fragment = new InputFieldsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InputFieldsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_fields, container, false);
    }

    EditText editTextEmail, editTextFname, editTextLname, editTextPasswordSignUp, editTextPasswordConfirm, editTextPhone;
    Spinner spinnerGender, spinnerCountry, spinnerCity;

    String[] countries = new String[4];
    String[] phoneCodes = new String[4];
    ArrayList<String> cities = new ArrayList<String>();

    ArrayAdapter<String> countryAd;
    ArrayAdapter<String> citiesAd;
    ArrayAdapter<String> genderAd;

    DataBaseHelper dataBaseHelper;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initialize relevant views
        editTextEmail = (EditText) getActivity().findViewById(R.id.editTextEmailSignUp);
        editTextFname = (EditText) getActivity().findViewById(R.id.editTextFname);
        editTextLname = (EditText) getActivity().findViewById(R.id.editTextLname);
        editTextPasswordSignUp = (EditText) getActivity().findViewById(R.id.editTextPasswordSignUp);
        editTextPasswordConfirm = (EditText) getActivity().findViewById(R.id.editTextPasswordConfirm);
        editTextPhone = (EditText) getActivity().findViewById(R.id.editTextPhone);

        spinnerGender = (Spinner) getActivity().findViewById(R.id.spinnerGenderSignUp);
        spinnerCountry = (Spinner) getActivity().findViewById(R.id.spinnerCountrySignUp);
        spinnerCity = (Spinner) getActivity().findViewById(R.id.spinnerCitySignUp);

        spinnerCountry.setOnItemSelectedListener(this);

        //set spinner contents
        genderAd = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, new String[]{"Male", "Female"});

        genderAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGender.setAdapter(genderAd);

        countryAd = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, countries);

        countryAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCountry.setAdapter(countryAd);

        citiesAd =  new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, cities);

        citiesAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCity.setAdapter(citiesAd);

        dataBaseHelper = new DataBaseHelper(getActivity(), User.dbName, null,1);


        Cursor cursorAllCountries =  dataBaseHelper.getAllCountries();

        int i = 0;
        // Get Country Spinner Codes
        while(cursorAllCountries.moveToNext()) {
            countries[i] = cursorAllCountries.getString(0);
            phoneCodes[i] = cursorAllCountries.getString(1);
            i++;
        }

        InputFieldsCommunicator inputFieldsCommunicator = (InputFieldsCommunicator) getActivity();
        inputFieldsCommunicator.respond();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        TextView textAreaPhoneCode = (TextView) getActivity().findViewById(R.id.textViewAreaCode);
        textAreaPhoneCode.setText(phoneCodes[position]);

        Cursor citiesCursor  = dataBaseHelper.getAllCitiesFromCountry(countries[position]);
        int j = 0;
        cities.clear();
        while(citiesCursor.moveToNext()){
            cities.add(String.valueOf(citiesCursor.getString(0)));
        }
        citiesAd.notifyDataSetChanged();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public String getEmail(){
        return editTextEmail.getText().toString();
    }

    public String getFname(){
        return editTextFname.getText().toString();
    }

    public String getLname(){
        return editTextLname.getText().toString();
    }

    public String getPassword(){
        return editTextPasswordSignUp.getText().toString();
    }

    public String getConfirmPassword(){
        return editTextPasswordConfirm.getText().toString();
    }

    public String getPhone(){
        return editTextPhone.getText().toString();
    }

    public String getGender(){
        return spinnerGender.getSelectedItem().toString();
    }

    public String getCountry(){
        return spinnerCountry.getSelectedItem().toString();
    }

    public String getCity(){
        return spinnerCity.getSelectedItem().toString();
    }

    public void seTexts(String Email, String fName, String lName, String password, String phone, String gender, String country, String city){
        editTextEmail.setText(Email);
        editTextFname.setText(fName);
        editTextLname.setText(lName);
        editTextPasswordSignUp.setText(password);
        editTextPhone.setText(phone);

        int countryIndex = 0, cityIndex = 0, genderIndex = 0;
        for (int i = 0; i < countries.length; i ++){
            if (countries[i].equals(country)){
                countryIndex = i;
                break;
            }
        }
        for (int i = 0; i < cities.size(); i ++){
            if (cities.get(i).equals(city)){
                cityIndex = i;
                break;
            }
        }
        if (gender.equals("Female"))
            genderIndex = 1;

        spinnerCountry.setSelection(countryIndex);
        spinnerCity.setSelection(cityIndex);
        spinnerGender.setSelection(genderIndex);

        editTextEmail.setEnabled(false);

    }



}