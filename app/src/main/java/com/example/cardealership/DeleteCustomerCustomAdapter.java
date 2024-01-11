package com.example.cardealership;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DeleteCustomerCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<User> list = new ArrayList<User>();
    private Context context;

    public DeleteCustomerCustomAdapter(ArrayList<User> list, Context context) {
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
            view = inflater.inflate(R.layout.delete_customer_list_item_layout, null);
        }

        //Handle TextView and display string from your list
        TextView textViewCustomer = (TextView)view.findViewById(R.id.textViewCustomer);
        textViewCustomer.setText(list.get(position).fName + list.get(position).lName);

        TextView textViewDate = (TextView) view.findViewById(R.id.textViewEmail);
        textViewDate.setText(list.get(position).email);

        Button buttonDeleteCustomer = (Button) view.findViewById(R.id.buttonDeleteCustomer);

        View finalView = view;
        buttonDeleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteCustomerActivity.calltoDeleteCustomer(finalView.getContext(), list.get(position).email);
                list.remove(position);
            }
        });


        return view;
    }
}