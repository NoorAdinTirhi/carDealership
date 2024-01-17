package com.example.cardealership;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;

public class SpecialOfferCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<SpecialOffer> list = new ArrayList<SpecialOffer>();
    private Context context;


    public SpecialOfferCustomAdapter(ArrayList<SpecialOffer> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.car_list_item, null);
        }
        View finalView = view;

        //Handle TextView and display string from your list
        TextView textViewCustomer = (TextView)view.findViewById(R.id.textViewCarName);
        textViewCustomer.setText(list.get(position).carModel + "   " + list.get(position).offer+"% OFF");

        Button buttonFavorites = (Button) view.findViewById(R.id.buttonFavorite);
        Button buttonReserves  = (Button) view.findViewById(R.id.buttonReserve);

        int flagUnfavorite = 0;
        int flagUnreserve = 0;
        SharedPrefManager sharedPrefManager =SharedPrefManager.getInstance(finalView.getContext());

        DataBaseHelper dataBaseHelper = new DataBaseHelper(finalView.getContext(), User.dbName, null, 1);

        Cursor favorites = dataBaseHelper.getFavoritesByCarID(finalView.getContext() ,list.get(position).carID);

        Cursor reserves = dataBaseHelper.getReservesByCarID(list.get(position).carID);

        while(favorites.moveToNext()){
            if (favorites.getString(0).equals(sharedPrefManager.readString("loggedInEmail", "Default")));{
                buttonFavorites.setText("UNFAVORITE");
                flagUnfavorite = 1;
            }
        }
        while(reserves.moveToNext()){
            if (reserves.getString(0).equals(sharedPrefManager.readString("loggedInEmail", "Default")));{
                buttonReserves.setText("UNRESERVE");
                flagUnreserve = 1;
            }
        }

        int [] finalFlagUnfavorite = {flagUnfavorite};
        int [] finalFlagUnreserve = {flagUnreserve};
        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpCommunicator communicator = (PopUpCommunicator) finalView.getContext();
                if (finalFlagUnfavorite[0] == 0) {
                    dataBaseHelper.addToFavorites(finalView.getContext(),sharedPrefManager.readString("loggedInEmail", "Default"),list.get(position).carID);
                    buttonFavorites.setText("UNFAVORITE");
                    finalFlagUnfavorite[0] = 1;
                    dataBaseHelper.insertHistoryAct(new HistoryAct(sharedPrefManager.readString("loggedInEmail", "Default"), new Date(), String.format("Add Car %d to favorites", list.get(position).carID)));
                }else{
                    dataBaseHelper.removeFromFavorites(sharedPrefManager.readString("loggedInEmail", "Default"),list.get(position).carID);
                    buttonFavorites.setText("FAVORITE");
                    finalFlagUnfavorite[0] = 0;
                    dataBaseHelper.insertHistoryAct(new HistoryAct(sharedPrefManager.readString("loggedInEmail", "Default"), new Date(), String.format("Removed Car %d from favorites", list.get(position).carID)));

                }
            }
        });

        buttonReserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalFlagUnreserve[0] == 0){
                    PopUpCommunicator communicator = (PopUpCommunicator) finalView.getContext();
                    communicator.respondSubmitPopUpLaucnh(list.get(position).carID, list.get(position).carModel, list.get(position).price);
                    buttonReserves.setEnabled(false);
                }else{
                    dataBaseHelper.removeReserve(list.get(position).carID, sharedPrefManager.readString("loggedInEmail", "Default"));
                    finalFlagUnreserve[0] = 0;
                    buttonReserves.setText("RESERVE");
                    dataBaseHelper.insertHistoryAct(new HistoryAct(sharedPrefManager.readString("loggedInEmail", "Default"), new Date(), String.format("Removed Car %d from reserves", list.get(position).carID)));
                }
            }
        });



        return view;
    }

    public void switchButton(int position){

    }
}