package com.example.cardealership;

public class User {
    static String dbName = "ourDataBase2";
    String email;

    public User(String email, String fName, String lName, String gender, String password, String country, String city, String phoneNO) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.gender = gender;
        this.password = password;
        this.country = country;
        this.city = city;
        this.phoneNO = phoneNO;
    }

    String fName;
    String lName;
    String gender;
    String password;
    String country;
    String city;
    String phoneNO;

    public String getEmail() {
        return email;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }

    @Override
    public String toString() {
        return "User{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", phoneNO='" + phoneNO + '\'' +
                '}';
    }

    public long getId() {
        return 5;
    }
}
