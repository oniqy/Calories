package com.example.testapp.DTO;

public class Weight {
    public int Weight;
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

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public String getControlWeight_Date() {
        return ControlWeight_Date;
    }

    public void setControlWeight_Date(String controlWeight_Date) {
        ControlWeight_Date = controlWeight_Date;
    }

    public Weight(int weight, String controlWeight_Date,String email) {
        Weight = weight;
        Email = email;
        ControlWeight_Date = controlWeight_Date;
    }
}
