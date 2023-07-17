package com.example.testapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testapp.R;

import java.util.ArrayList;

public class adapter_mucTieu extends BaseAdapter {
    ArrayList<item_mucTieu> item_mucTieus = new ArrayList<>();
    LayoutInflater inflater;
    int layout;

    public adapter_mucTieu(LayoutInflater layoutInflater, ArrayList<item_mucTieu> item_mucTieus, int item_tapluyen) {
        this.inflater = layoutInflater;
        this.item_mucTieus = item_mucTieus;
        this.layout = item_tapluyen;
    }


    @Override
    public int getCount() {
        return item_mucTieus.size();
    }

    @Override
    public Object getItem(int position) {
        return item_mucTieus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item_mucTieu item = item_mucTieus.get(position);
        View rowView = inflater.inflate(layout,null,true);
        TextView chedo = (TextView) rowView.findViewById(R.id.tv_chedo);
        chedo.setText(item.getChedo());
        TextView mota = (TextView) rowView.findViewById(R.id.tv_mota);
        mota.setText(item.getMota());
        return rowView;
    }
}
