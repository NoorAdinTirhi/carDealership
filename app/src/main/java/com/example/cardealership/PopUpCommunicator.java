package com.example.cardealership;

public interface PopUpCommunicator {

    public void respondPopUpExit();

    public void respondPopUpLaunch(int ID, String type, int price);
    public void respondOpenFragment();

    public void addToFavourites(String email, int carID);

    public void respondSubmitPopUpLaucnh(int ID, String type, int price);

    public void respondSubmitPopUpExit();

}
