package com.example.testapp.adapter;

import java.util.ArrayList;

public class item_mucTieu {
    String chedo;
    String mota;

    public String getChedo() {
        return chedo;
    }

    public void setChedo(String chedo) {
        this.chedo = chedo;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public item_mucTieu(String chedo, String mota) {
        this.chedo = chedo;
        this.mota = mota;
    }
    public static ArrayList<item_mucTieu> initMuctieu(String[] chedo, String[] mota){
        ArrayList<item_mucTieu> item_mucTieus = new ArrayList<>();
        for(int i = 0; i< chedo.length;i++){
            item_mucTieu item = new item_mucTieu(chedo[i],mota[i]);
            item_mucTieus.add(item);
        }
        return item_mucTieus;
    }
}
