package com.example.cardealership;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarJsonParser {

    static Random rand = new Random();
    public static ArrayList<Car> getObjectFromJson(String json) {
        ArrayList<Car> cars;
        try {
            JSONArray jsonArray = new JSONArray(json);
            cars = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Car car = new Car();
                car.setId(jsonObject.getInt("id"));
                car.setType(jsonObject.getString("type"));
                car.setPrice(rand.nextInt(70000) + 30000);
                cars.add(car);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return cars;
    }
}

