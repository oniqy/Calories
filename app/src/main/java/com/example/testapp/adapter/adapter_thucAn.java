package com.example.testapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapp.DTO.FoodMenu;
import com.example.testapp.R;

import java.util.ArrayList;

public class adapter_thucAn extends BaseAdapter {
    private ArrayList<FoodMenu> listData = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    @Override
    public int getCount() {
        return 0;
    }
    public adapter_thucAn(ArrayList<FoodMenu> listData, Context context) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    static class ViewHoler{
        TextView name;
        TextView cs;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view1 = convertView;
        ViewHoler holer;
        if(convertView ==null){
            convertView = layoutInflater.inflate(R.layout.item_food,null);
            holer = new ViewHoler();
            holer.name=(TextView) convertView.findViewById(R.id.textView_FoodName);
            holer.cs = (TextView) convertView.findViewById(R.id.textView_Gr);
            convertView.setTag(holer);
        }else {
            holer = (ViewHoler) convertView.getTag();
        }
        FoodMenu foodMenus = this.listData.get(position);
//        holer.img.setImageResource(foodMenus.getImg());
        holer.name.setText(foodMenus.foodName);
        holer.cs.setText(foodMenus.sl);
//        int imageId = this.getMipmapResIdByName(foodMenus.getImg());
//        holer.img.setImageResource(imageId);

        return convertView;
    }
}
