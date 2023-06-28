package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DangNhapPage extends AppCompatActivity {
    Button btn_dk,btn_dn;
    EditText edt_userName,edt_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dang_nhap_page);
        addControl();
        addEvent();
    }
    private void addControl(){
        btn_dk = (Button) findViewById(R.id.btn_dk);
        btn_dn=(Button) findViewById(R.id.btn_dn);
        edt_userName = (EditText) findViewById(R.id.edt_userName);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
    }
    private void addEvent(){
        btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trangDangKy = new Intent(DangNhapPage.this,DangKyPage.class);
                startActivity(trangDangKy);
                addControl();
                addEvent();
            }
        });
        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trangChu = new Intent(DangNhapPage.this,Info.class);
                startActivity(trangChu);
            }
        });
    }
}