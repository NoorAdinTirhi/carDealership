package com.example.cardealership;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE COUNTRY(" +
                "Name VARCHAR(50) PRIMARY KEY," +
                "PhoneCode VARCHAR(5) NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE CITY(" +
                "Name VARCHAR(50) PRIMARY KEY," +
                "Country VARCHAR(50) NOT NULL," +
                "FOREIGN KEY(Country) REFERENCES COUNTRY(Name)" +
                ")");

        db.execSQL("CREATE TABLE USER(" +
                "Email VARCHAR(50) PRIMARY KEY," +
                "Fname VARCHAR(50) NOT NULL," +
                "Lname VARCHAR(50) NOT NULL," +
                "Gender TEXT CHECK(Gender IN ('Female', 'Male')) NOT NULL," +
                "Password VARCHAR(50) NOT NULL," +
                "City VARCHAR(50)," +
                "Admin BOOLEAN NOT NULL DEFAULT 0," +
                "CurrentUser BOOLEAN NOT NULL DEFAULT 0," +
                "PhoneNo VARCHAR(9) NOT NULL," +
                "FOREIGN KEY(CITY) REFERENCES CITY(Name)" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertUser(Context context ,User user){
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Email", user.getEmail());
            contentValues.put("Fname", user.getfName());
            contentValues.put("Lname", user.getlName());
            contentValues.put("Gender", user.getGender());
            contentValues.put("Password", user.getPassword());
            contentValues.put("City", user.getCity());
            contentValues.put("PhoneNo", user.getPhoneNO());
            sqLiteDatabase.insert("USER", null, contentValues);
        }catch(SQLiteConstraintException e){
            Toast.makeText(context, "Email already registered", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertAdminUser(Context context ,User user, Boolean admin){
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Email", user.getEmail());
            contentValues.put("Fname", user.getfName());
            contentValues.put("Lname", user.getlName());
            contentValues.put("Gender", user.getGender());
            contentValues.put("Password", user.getPassword());
            contentValues.put("City", user.getCity());
            contentValues.put("PhoneNo", user.getPhoneNO());
            contentValues.put("Admin", admin);
            sqLiteDatabase.insert("USER", null, contentValues);
        }catch(SQLiteConstraintException e){
            Toast.makeText(context, "Email already registered", Toast.LENGTH_SHORT).show();
        }
    }

    public void setCurrentUser(String email){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        ContentValues contentValues2 = new ContentValues();

        contentValues1.put("CurrentUser", true);
        contentValues2.put("CurrentUser", false);

        sqLiteDatabase.update("USER", contentValues2,null,null);
        sqLiteDatabase.update("USER", contentValues1, "Email = ?", new String[]{email});
    }

    public Cursor getAllUsers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER", null);
    }

    public Cursor getUserEmailPassword(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE Email = ?", new String[]{email});
    }

    public void insertCity(String name, String country){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Country", country);
        sqLiteDatabase.insert("City", null, contentValues);
    }

    public Cursor getAllCities(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM CITY", null);
    }

    public Cursor getAllCitiesFromCountry(String country){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] countries = {country};
        return sqLiteDatabase.rawQuery("SELECT * FROM CITY WHERE Country = ?", countries);
    }

    public void insertCountry(String name, String code){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("PhoneCode", code);
        sqLiteDatabase.insert("Country", null, contentValues);
    }

    public Cursor getAllCountries(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM COUNTRY", null);
    }

    public void clearTables(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("CITY", null, null);
        sqLiteDatabase.delete("COUNTRY", null, null);
//        sqLiteDatabase.delete("USER", null, null);
    }

    public void fillCities(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
    }

}
