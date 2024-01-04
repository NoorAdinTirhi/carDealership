package com.example.cardealership;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "LoggedInUser";
    private static final int SHARED_PREF_PRIVATE = Context.MODE_PRIVATE;
    private static SharedPrefManager ourinstance = null;
    private static SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    private SharedPrefManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_PRIVATE);
        editor = sharedPreferences.edit();
    }

    static SharedPrefManager getInstance(Context context){
        if (ourinstance != null){
            return ourinstance;
        }
        ourinstance = new SharedPrefManager(context);
        return ourinstance;
    }

    public boolean writeString(String key, String value){
        editor.putString(key, value);
        return editor.commit();
    }

    public String readString(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }


}
