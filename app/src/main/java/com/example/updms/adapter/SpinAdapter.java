package com.example.updms.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.updms.rest.Response.District;
import com.example.updms.rest.Response.Division;
import com.example.updms.rest.Response.States;

import java.util.List;


public class SpinAdapter<T> extends ArrayAdapter<T> {
    private Context context;
    private List<T> values;
    private int type;

    private Object object;

    public SpinAdapter(Context context, int textViewResourceId, List<T> values,int type) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
        this.type =type;
    }

    public int getCount() {
        return values.size();
    }

    public T getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        if(type==1){
            label.setText(((States)values.get(position)).getStateName());
        }else if(type==2){
            label.setText(((Division)values.get(position)).getDivisionName());

        }else if(type==3){
            label.setText(((District)values.get(position)).getDistrictName());

        }

        label.setTextColor(Color.BLACK);
        label.setPadding(10,10,10,10);
        label.setTextSize(20);
//        label.setText(values.toArray(new Object[values.size()])[position]
//                .toString());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        if(type==1){
            label.setText(((States)values.get(position)).getStateName());
        }else if(type==2){
            label.setText(((Division)values.get(position)).getDivisionName());

        }else if(type==3){
            label.setText(((District)values.get(position)).getDistrictName());

        }
        label.setTextColor(Color.BLACK);
        label.setPadding(10,10,10,10);
        label.setTextSize(20);


        return label;
    }
}

