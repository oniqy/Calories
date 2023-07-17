package com.example.testapp.adapter;

import java.util.ArrayList;

public class item_gioitinh {
    int img;
    String sex;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public item_gioitinh(int img, String sex) {
        this.img = img;
        this.sex = sex;
    }
    public static ArrayList<item_gioitinh> initSex(int[] img1, String[] sex2){
        ArrayList<item_gioitinh> item_gioitinhs = new ArrayList<>();
        for(int i = 0;i<sex2.length;i++){
            item_gioitinh item = new item_gioitinh(img1[i],sex2[i]);
            item_gioitinhs.add(item);
        }
        return item_gioitinhs;
    }
}
