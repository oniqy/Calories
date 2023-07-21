package com.example.testapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.DTO.FoodMenu;
import com.example.testapp.R;

import java.util.ArrayList;

public class adapter_dailyFood extends RecyclerView.Adapter<adapter_dailyFood.MyViewHolder> {

    ArrayList<FoodMenu> foodMenus = new ArrayList<>();

    public adapter_dailyFood(ArrayList<FoodMenu> foodMenus) {
        this.foodMenus = foodMenus;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FoodMenu item = foodMenus.get(position);
        holder.textView_FoodName.setText(String.valueOf(item.getFoodName()));
        holder.textView_Gr.setText(String.valueOf(item.getsl()));
    }

    @Override
    public int getItemCount() {
        return foodMenus.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView_FoodName,textView_Gr;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_FoodName = (TextView) itemView.findViewById(R.id.textView_FoodName);
            textView_Gr = (TextView) itemView.findViewById(R.id.textView_Gr);
        }
    }
}
