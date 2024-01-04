package com.example.cardealership;

public class Car {
    private String type;
    private int id;

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
}
