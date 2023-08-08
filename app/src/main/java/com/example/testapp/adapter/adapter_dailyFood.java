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

public class adapter_dailyFood extends RecyclerView.Adapter<adapter_dailyFood.MyViewHolder> {

    ArrayList<DaulyFood> foodMenus = new ArrayList<>();
    String idDate=null;
    public adapter_dailyFood(ArrayList<DaulyFood> foodMenus) {
        this.foodMenus = foodMenus;
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        return new MyViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DaulyFood item = foodMenus.get(position);
        holder.textView_FoodName.setText(String.valueOf(item.getNameFoodOfday()));
        idDate = item.getIdDate();

    }


    @Override
    public int getItemCount() {
        return foodMenus.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_FoodName;
        private ImageButton imgDeleteFood;
        private adapter_dailyFood dailyFood;
        public MyViewHolder(@NonNull View itemView,OnItemClick listener) {
            super(itemView);
            textView_FoodName = (TextView) itemView.findViewById(R.id.textView_FoodName);
            imgDeleteFood = (ImageButton) itemView.findViewById(R.id.imgDeleteFood);

            imgDeleteFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onIttemClick(getAdapterPosition());
                }
            });
        }
    }

}
