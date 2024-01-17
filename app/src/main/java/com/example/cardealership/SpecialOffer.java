package com.example.cardealership;

public class SpecialOffer {
    public int carID;
    public int price;
    public String carModel;
    public int offer;

    public SpecialOffer(int carID, String carModel, int offer, int price) {
        this.carID = carID;
        this.carModel = carModel;
        this.offer = offer;
        this.price = price;
    }
}


