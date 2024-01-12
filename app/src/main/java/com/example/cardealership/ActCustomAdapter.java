package com.example.cardealership;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ActCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<HistoryAct> list = new ArrayList<HistoryAct>();
    private Context context;

    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public ActCustomAdapter (ArrayList<HistoryAct> list, Context context) {
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
            view = inflater.inflate(R.layout.history_act_list_item, null);
        }

        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewDate.setText(formatter.format(list.get(position).actDate));
        TextView textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
        textViewDescription.setText(list.get(position).actDescription);

        return view;
    }
}