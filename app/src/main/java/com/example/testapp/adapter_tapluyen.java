package com.example.testapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_tapluyen  extends BaseAdapter {
    ArrayList<item_tapluyen> item_tapluyens = new ArrayList<>();
    LayoutInflater inflater;
    int layout;

    public adapter_tapluyen(LayoutInflater inflater, ArrayList<item_tapluyen> item_tapluyens, int layout) {
        this.inflater = inflater;
        this.item_tapluyens = item_tapluyens;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return item_tapluyens.size();
    }

    @Override
    public Object getItem(int position) {
        return item_tapluyens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item_tapluyen item = item_tapluyens.get(position);
        View rowView = inflater.inflate(layout,null,true);
        TextView chedo = (TextView) rowView.findViewById(R.id.tv_chedo);
        chedo.setText(item.getChedo());
        TextView mota = (TextView) rowView.findViewById(R.id.tv_mota);
        mota.setText(item.getMota());
        return rowView;
    }
}
