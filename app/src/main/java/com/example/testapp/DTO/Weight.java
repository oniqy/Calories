package com.example.testapp.DTO;

import java.util.ArrayList;
import java.util.List;

public class Weight {
    public double Weight;
    public String ControlWeight_Date;
    public String Email;
    public Weight() {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public String getControlWeight_Date() {
        return ControlWeight_Date;
    }

    public void setControlWeight_Date(String controlWeight_Date) {
        ControlWeight_Date = controlWeight_Date;
    }

    public Weight(double weight, String controlWeight_Date,String email) {
        Weight = weight;
        Email = email;
        ControlWeight_Date = controlWeight_Date;
    }
    public static ArrayList<Weight> initWeight(List<Double> Weight, List<String> date, List<String> email){
        ArrayList<Weight> weights = new ArrayList<>();
        for(int i = 0;i<Weight.size();i++){
            Weight item = new Weight(Weight.get(i), date.get(i), email.get(i));
            weights .add(item);
        }
        return weights;
    }
}
