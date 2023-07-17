package com.example.testapp.DTO;

import java.util.ArrayList;

public class FoodMenu {
    public int idFood;
    public String foodName;
    public  int Calories;
    public int Fats;
    public int Proteins;
    public int Carbs;
    public String sl;

    public FoodMenu(int idFood, String foodName, int calories, int fats, int proteins, int carbs, String sl) {
        this.idFood = idFood;
        this.foodName = foodName;
        Calories = calories;
        Fats = fats;
        Proteins = proteins;
        Carbs = carbs;
        sl = sl;
    }

    public FoodMenu() {

    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public int getFats() {
        return Fats;
    }

    public void setFats(int fats) {
        Fats = fats;
    }

    public int getProteins() {
        return Proteins;
    }

    public void setProteins(int proteins) {
        Proteins = proteins;
    }

    public int getCarbs() {
        return Carbs;
    }

    public void setCarbs(int carbs) {
        Carbs = carbs;
    }

    public String getsl() {
        return sl;
    }

    public void setsl(String sl) {
        this.sl = sl;
    }
    public static ArrayList<FoodMenu> initfood(int[] idFood,String[] name,int[] Calories,int[] Fats,int[] Proteins,int[] Carbs,String[] sl){
        ArrayList<FoodMenu> foodMenus = new ArrayList<>();
        for(int i = 0;i<name.length;i++){
            FoodMenu item = new FoodMenu(idFood[i],name[i],Calories[i],Fats[i],Proteins[i],Carbs[i],sl[i]);
            foodMenus .add(item);
        }
        return foodMenus;
    }
}
