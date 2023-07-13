package com.example.testapp.DTO;

public class UserInfo {
    public  int idUser ;
    public  String Name ;
    public  int UserHeight ;
    public  int UserWeight ;
    public  int BirthDay ;
    public  String Exercise;
    public  String Gender;
    public  String Target ;

    public UserInfo(int idUser, String name, int userHeight, int userWeight, int birthDay, String exercise, String gender, String target) {
        this.idUser = idUser;
        this.Name = name;
        this.UserHeight = userHeight;
        this.UserWeight = userWeight;
        this.BirthDay = birthDay;
        this.Exercise = exercise;
        this.Gender = gender;
        this.Target = target;
    }

    public UserInfo() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getUserHeight() {
        return UserHeight;
    }

    public void setUserHeight(int userHeight) {
        UserHeight = userHeight;
    }

    public int getUserWeight() {
        return UserWeight;
    }

    public void setUserWeight(int userWeight) {
        UserWeight = userWeight;
    }

    public int getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(int birthDay) {
        BirthDay = birthDay;
    }

    public String getExercise() {
        return Exercise;
    }

    public void setExercise(String exercise) {
        Exercise = exercise;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }
}
