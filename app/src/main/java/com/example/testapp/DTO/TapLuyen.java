package com.example.testapp.DTO;

public class TapLuyen {
    int ExerciseMenu_id;
    Double Excercise_calo;
    String ExerciseMenu_name;


    public TapLuyen(){};
    public TapLuyen(int exerciseMenu_id, double excercise_calo, String exerciseMenu_name) {
        ExerciseMenu_id = exerciseMenu_id;
        Excercise_calo = excercise_calo;
        ExerciseMenu_name = exerciseMenu_name;
    }

    public int getExerciseMenu_id() {
        return ExerciseMenu_id;
    }

    public void setExerciseMenu_id(int exerciseMenu_id) {
        ExerciseMenu_id = exerciseMenu_id;
    }

    public double getExcercise_calo() {
        return Excercise_calo;
    }

    public void setExcercise_calo(double excercise_calo) {
        Excercise_calo = excercise_calo;
    }


    public String getExerciseMenu_name() {
        return ExerciseMenu_name;
    }

    public void setExerciseMenu_name(String exerciseMenu_name) {
        ExerciseMenu_name = exerciseMenu_name;
    }
}
