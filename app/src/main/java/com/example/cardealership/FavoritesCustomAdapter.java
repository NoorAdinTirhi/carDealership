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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class FavoritesCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Favorites> list = new ArrayList<Favorites>();
    private Context context;

    public FavoritesCustomAdapter(ArrayList<Favorites> list, Context context) {
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
        //just return 0 if your list items do not have an Id variable.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.favorite_list_item, null);
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(view.getContext(), User.dbName, null,1);
        SharedPrefManager sharedPrefManager =SharedPrefManager.getInstance(view.getContext());


        //Handle TextView and display string from your list
        TextView textViewCarName = (TextView)view.findViewById(R.id.textViewCarName);
        textViewCarName.setText(list.get(position).carType + ": " + list.get(position).carID);
//        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
//        textViewDate.setText(list.get(position).reserveDate);
        Button buttonDeleteReservation = (Button) view.findViewById(R.id.buttonReserve);

        View finalView = view;

        Cursor reserves = dataBaseHelper.getReservesByCarID(Integer.parseInt(list.get(position).carID));

        final int flag[] = {0};

        while(reserves.moveToNext()){
            if (reserves.getString(0).equals(sharedPrefManager.readString("loggedInEmail", "Default")));{
                buttonDeleteReservation.setText("UNRESERVE");
                flag[0] = 1;
            }
        }


        buttonDeleteReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag[0] == 0) {
                    PopUpCommunicator communicator = (PopUpCommunicator) finalView.getContext();
                    communicator.respondSubmitPopUpLaucnh(Integer.parseInt(list.get(position).carID), list.get(position).carType, list.get(position).carPrice);
                }else{
                    dataBaseHelper.removeReserve(Integer.parseInt(list.get(position).carID), sharedPrefManager.readString("loggedInEmail", "Default"));
                    buttonDeleteReservation.setText("RESERVE CAR");
                    flag[0] = 0;
                    dataBaseHelper.insertHistoryAct(new HistoryAct(sharedPrefManager.readString("loggedInEmail", "Default"), new Date(), String.format("Removed Car %d from favorites", Integer.parseInt(list.get(position).carID))));

                }
            }
        });

        return view;
    }
}