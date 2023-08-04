package com.example.testapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.DTO.DaulyFood;
import com.example.testapp.DTO.Weight;
import com.example.testapp.R;

import java.util.ArrayList;

public class adapter_lichsu extends RecyclerView.Adapter<adapter_lichsu.MyViewHolder>{
    ArrayList<Weight> weights = new ArrayList<>();
    String idDate=null;
    public adapter_lichsu(ArrayList<Weight> weights) {
        this.weights = weights;
    }
    private OnItemClick listener;
    public interface OnItemClick {
        void onIttemClick(int position);
    }
    public void setOnItemClick(OnItemClick click){
        listener = click;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichsu,parent,false);
        return new MyViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Weight item = weights.get(position);
        holder.textView_FoodName.setText(String.valueOf(item.getControlWeight_Date()));
        holder.textView_Gr.setText(String.valueOf(item.getWeight()));
    }


    @Override
    public int getItemCount() {
        return weights.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_FoodName,textView_Gr;

        public MyViewHolder(@NonNull View itemView,OnItemClick listener) {
            super(itemView);
            textView_FoodName = (TextView) itemView.findViewById(R.id.textView_FoodName);
            textView_Gr = (TextView) itemView.findViewById(R.id.textView_Gr);
        }
    }

}
