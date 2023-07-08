package com.example.testapp.SQL;

public class UserAcc {
    private String email;
    private  String passWord;

    public UserAcc(String email, String passWord) {
        this.email = email;
        this.passWord = passWord;
    }

    public UserAcc() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

