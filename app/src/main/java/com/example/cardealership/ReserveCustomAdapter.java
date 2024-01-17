package com.example.cardealership;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReserveCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Reserves> list = new ArrayList<Reserves>();
    private Context context;

    public ReserveCustomAdapter(ArrayList<Reserves> list, Context context) {
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
            view = inflater.inflate(R.layout.admin_reserves_list_item_layout, null);
        }

        //Handle TextView and display string from your list
        TextView textViewCustomer = (TextView)view.findViewById(R.id.textViewCustomer);
        textViewCustomer.setText(list.get(position).customerName);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewDate.setText(list.get(position).reserveDate);
        TextView textViewCarType = (TextView) view.findViewById(R.id.textViewCarType);
        textViewCarType.setText(list.get(position).carType);
        TextView textViewCarID = (TextView) view.findViewById(R.id.textViewCarID);
        textViewCarID.setText(list.get(position).carID);

        return view;
    }
}