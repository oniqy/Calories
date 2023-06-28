package com.example.testapp;

import java.util.ArrayList;

public class item_tapluyen {
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

    public item_tapluyen(String chedo, String mota) {
        this.chedo = chedo;
        this.mota = mota;
    }
    public static ArrayList<item_tapluyen> inittapluyen(String[] chedo, String[] mota){
        ArrayList<item_tapluyen> item_tapluyens = new ArrayList<>();
        for(int i = 0; i< chedo.length;i++){
            item_tapluyen item = new item_tapluyen(chedo[i],mota[i]);
            item_tapluyens.add(item);
        }
        return item_tapluyens;
    }
}
