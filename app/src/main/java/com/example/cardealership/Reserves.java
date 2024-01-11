package com.example.cardealership;

public class Reserves {
    public String customerName;
    public String reserveDate;
    public String carType;
    public String carID;

    public Reserves(String customerName, String reserveDate, String carModel, String carID) {
        this.customerName = customerName;
        this.reserveDate = reserveDate;
        this.carType = carModel;
        this.carID = carID;
    }

    public long getId() {
        return 5;
    }
}
