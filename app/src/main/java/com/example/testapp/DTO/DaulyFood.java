package com.example.testapp.DTO;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaulyFood {
    public String IdDate;
    public String NameFoodOfday;
    public  int idFood;

    public DaulyFood(String idDate, String nameFoodOfday, int idFood, String timeofDay) {
        IdDate = idDate;
        NameFoodOfday = nameFoodOfday;
        this.idFood = idFood;
        TimeofDay = timeofDay;
    }

    public String TimeofDay;

    public DaulyFood() {

    }

    public String getIdDate() {
        return IdDate;
    }

    public void setIdDate(String idDate) {
        IdDate = idDate;
    }

    public String getNameFoodOfday() {
        return NameFoodOfday;
    }

    public void setNameFoodOfday(String nameFoodOfday) {
        NameFoodOfday = nameFoodOfday;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getTimeofDay() {
        return TimeofDay;
    }

    public void setTimeofDay(String timeofDay) {
        TimeofDay = timeofDay;
    }
    public static ArrayList<DaulyFood> initfood(List<String> IdDate, List<String> NameFoodOfday, List<Integer> idFood, List<String> TimeofDay){
        ArrayList<DaulyFood> DaulyFoods = new ArrayList<>();
        for(int i = 0;i<IdDate.size();i++){
            DaulyFood item = new DaulyFood(IdDate.get(i),NameFoodOfday.get(i),idFood.get(i),TimeofDay.get(i));

            DaulyFoods .add(item);
            Log.v("initfood", "" + DaulyFoods.get(i));
        }

        return DaulyFoods;
    }


}
