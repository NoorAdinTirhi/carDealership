package com.example.cardealership;

import android.content.Context;
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

public class ReservationCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Reserves> list = new ArrayList<Reserves>();
    private Context context;

    public ReservationCustomAdapter(ArrayList<Reserves> list, Context context) {
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
//        return 5;
        return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.reservations_list_item, null);
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(view.getContext(), User.dbName, null,1);
        SharedPrefManager sharedPrefManager =SharedPrefManager.getInstance(view.getContext());


        //Handle TextView and display string from your list
        TextView textViewCarName = (TextView)view.findViewById(R.id.textViewCarName);
        textViewCarName.setText(list.get(position).carType + ": " + list.get(position).carID);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewDate.setText(list.get(position).reserveDate);
        Button buttonDeleteReservation = (Button) view.findViewById(R.id.buttonDeleteReservation);

        View finalView = view;
        buttonDeleteReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.removeReserve(Integer.parseInt(list.get(position).carID), sharedPrefManager.readString("loggedInEmail", "Default"));
                Toast.makeText(finalView.getContext(),"REMOVED RESERVATION OF "+list.get(position).carType+": "+list.get(position).carID, Toast.LENGTH_SHORT).show();
                dataBaseHelper.insertHistoryAct(new HistoryAct(sharedPrefManager.readString("loggedInEmail", "Default")
                        ,new Date(),"Removed Reservation On " + list.get(position).carType + ": " + String.valueOf(list.get(position).carID)));
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}