package com.example.testapp.DTO;

import java.util.Date;

public class DailyCalories {
    public Date id;
    public int idExercise;
    public  int idFood;
    public String TimeofDay;
    public String NameFoodOfday;
    public String NameExerciseOfDay;

    public String getNameFoodOfday() {
        return NameFoodOfday;
    }

    public void setNameFoodOfday(String nameFoodOfday) {
        NameFoodOfday = nameFoodOfday;
    }

    public String getNameExerciseOfDay() {
        return NameExerciseOfDay;
    }

    public void setNameExerciseOfDay(String nameExerciseOfDay) {
        NameExerciseOfDay = nameExerciseOfDay;
    }

    public DailyCalories() {

    }

    public Date getId() {
        return id;
    }

    public void setId(Date id) {
        this.id = id;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
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

    public String setTimeofDay(String timeofDay) {
        this.TimeofDay = timeofDay;
        return timeofDay;
    }

    public DailyCalories(Date id, int idExercise, int idFood, String timeofDay,String NameFoodOfday,String NameExerciseOfDay) {
        this.id = id;
        this.idExercise = idExercise;
        this.idFood = idFood;
        TimeofDay = timeofDay;
        this.NameFoodOfday = NameFoodOfday;
        this.NameExerciseOfDay = NameExerciseOfDay;
    }


}
