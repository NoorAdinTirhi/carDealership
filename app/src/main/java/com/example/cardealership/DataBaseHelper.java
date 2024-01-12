package com.example.cardealership;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBaseHelper extends SQLiteOpenHelper {

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
                "PhoneNo VARCHAR(9) NOT NULL," +
                "FOREIGN KEY(CITY) REFERENCES CITY(Name)" +
                ")");

        db.execSQL("CREATE TABLE CAR(" +
                "ID INTEGER PRIMARY KEY," +
                "Type VARCHAR(50) NOT NULL," +
                "Price INTEGER NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE FAVORITES(" +
                "Email VARCHAR(50) NOT NULL," +
                "CarID INTEGER NOT NULL," +
                "FOREIGN KEY(Email) REFERENCES USER(Email) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE," +
                "FOREIGN KEY(CarID) REFERENCES CAR(ID) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE," +
                "PRIMARY KEY(Email, CarID)" +
                ")");

        db.execSQL("CREATE TABLE RESERVATION(" +
                "Email VARCHAR(50) NOT NULL," +
                "CarID INTEGER NOT NULL," +
                "ReserveDate DATE NOT NULL," +
                "FOREIGN KEY(Email) REFERENCES USER(Email) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE," +
                "FOREIGN KEY(CarID) REFERENCES CAR(ID) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE," +
                "PRIMARY KEY(Email, CarID)" +
                ")");

        db.execSQL("CREATE TABLE HISTORY(" +
                "ActID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Email VARCHAR(50) NOT NULL," +
                "ActDescription VARCHAR(256) NOT NULL," +
                "ActDate Date NOT NULL," +
                "FOREIGN KEY(Email) REFERENCES USER(Email) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE" +
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

    public Cursor getAllUsers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER", null);
    }

    public Cursor getUserEmailPassword(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE Email = ?", new String[]{email});
    }

    public Cursor getNonAdmins (){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE Admin = ?", new String[]{"0"});
    }

    public void deleteUser(String email){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("USER", "Email = ?", new String[]{email});
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

    public void insertCar(int ID, String type, int price){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", ID);
        contentValues.put("Type", type);
        contentValues.put("Price", price);
        sqLiteDatabase.insert("Car", null, contentValues);

    }

    public Cursor getAllCars(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM CAR", null);
    }

    public void clearTables(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("CITY", null, null);
        sqLiteDatabase.delete("COUNTRY", null, null);
        sqLiteDatabase.delete("USER", null, null);
        sqLiteDatabase.delete("CAR", null, null);
        sqLiteDatabase.delete("FAVORITES", null, null);
        sqLiteDatabase.delete("RESERVATION", null, null);
        sqLiteDatabase.delete("HISTORY", null, null);
    }

    public void insertReservation(String email, int carID, Date reserveDate){
        String dateString = formatter.format(reserveDate);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("CarID", carID);
        contentValues.put("ReserveDate", dateString);
        sqLiteDatabase.insert("RESERVATION", null, contentValues);
    }

    public Cursor getReservations(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT USER.fName AS fName, USER.lName AS lName, RESERVATION.ReserveDate AS date, CAR.Type AS Type, CAR.ID AS carID FROM CAR,RESERVATION,USER WHERE USER.Email = RESERVATION.Email AND CAR.ID = RESERVATION.CarID", null);
    }

    public void insertHistoryAct(HistoryAct historyAct){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", historyAct.email);
        contentValues.put("ActDate", formatter.format(historyAct.actDate));
        contentValues.put("ActDescription", historyAct.actDescription);
        sqLiteDatabase.insert("HISTORY", null, contentValues);
    }

    public Cursor getUserHistory(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM HISTORY WHERE Email = ?", new String[]{email});
    }

    public Cursor getAllHistory(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM HISTORY",null);
    }

    public void addToFavorites(Context context, String email, int carID){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("CarID", carID);
        sqLiteDatabase.insert("FAVORITES", null, contentValues);
        Toast.makeText(context, String.format("Car %d  added to %s favorties", carID, email), Toast.LENGTH_SHORT).show();
    }

    public void removeFromFavorites(String email, int carID){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("FAVORITES", "Email = ? and CarID = ?", new String[]{email, String.valueOf(carID)});
    }

    public Cursor getFavoritesByCarID (Context context, int carID){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM FAVORITES WHERE CarID = ?",new String[]{String.valueOf(carID)});
    }

    public Cursor getReservesByCarID (int carID){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM RESERVATION WHERE CarID = ?",new String[]{String.valueOf(carID)});
    }

    public void removeReserve (int carID, String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.delete("RESERVATION", "Email = ? and CarID = ?", new String[]{email, String.valueOf(carID)});
    }

    public void addToReserves(Context context, String email, int carID, Date reserveDate){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("CarID", carID);
        contentValues.put("ReserveDate", formatter.format(reserveDate));
        sqLiteDatabase.insert("RESERVATION", null, contentValues);
        Toast.makeText(context, String.format("Car %d  added to %s reservations", carID, email), Toast.LENGTH_SHORT).show();
    }

}
