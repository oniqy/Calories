package com.example.testapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_gioitinh extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<item_gioitinh> item_gioitinhs = new ArrayList<>();
    int layout;

    public adapter_gioitinh(LayoutInflater inflater, ArrayList<item_gioitinh> item_gioitinhs, int layout) {
        this.inflater = inflater;
        this.item_gioitinhs = item_gioitinhs;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return item_gioitinhs.size();
    }

    @Override
    public Object getItem(int position) {
        return item_gioitinhs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        item_gioitinh item =item_gioitinhs.get(position);
        View rowView  = inflater.inflate(layout,null,true);
        TextView sex = (TextView) rowView.findViewById(R.id.tv_gioitinh);
        sex.setText(item.getSex());
        ImageView img = (ImageView) rowView.findViewById(R.id.img_gioitinh);
        img.setImageResource(item.getImg());
        return rowView;
    }
}
