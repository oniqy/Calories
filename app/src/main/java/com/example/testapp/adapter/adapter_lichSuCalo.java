package com.example.testapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.DTO.DaulyFood;
import com.example.testapp.DTO.Weight;
import com.example.testapp.R;

import java.util.ArrayList;

public class adapter_lichSuCalo extends RecyclerView.Adapter<adapter_lichSuCalo.MyViewHolder>{
    @NonNull
    ArrayList<DaulyFood> daulyFoods = new ArrayList<>();

    public adapter_lichSuCalo(@NonNull ArrayList<DaulyFood> daulyFoods) {
        this.daulyFoods = daulyFoods;
    }

    @Override
    public adapter_lichSuCalo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(com.example.testapp.R.layout.item_lichsucalo,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_lichSuCalo.MyViewHolder holder, int position) {
        DaulyFood item = daulyFoods.get(position);
        holder.textView_FoodName.setText(String.valueOf(item.getNameFoodOfday()));
        holder.textView_date.setText(String.valueOf(item.getIdDate()));
        holder.textView_buaAn.setText(String.valueOf(item.getTimeofDay()));
    }

    @Override
    public int getItemCount() {
        return daulyFoods.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_FoodName,textView_date,textView_buaAn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_FoodName = (TextView) itemView.findViewById(R.id.textView_FoodName);
            textView_date = (TextView) itemView.findViewById(R.id.textView_date);
            textView_buaAn = (TextView) itemView.findViewById(R.id.textView_buaAn);
        }
    }
}
