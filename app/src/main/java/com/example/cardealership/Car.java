package com.example.cardealership;

import java.util.ArrayList;

public class Car {
    static ArrayList<Car> carList = new ArrayList<Car>();
    private String type;
    private int id;
    private int price;

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", id=" + id +
                '}';
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
