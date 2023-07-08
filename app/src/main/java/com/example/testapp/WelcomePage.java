package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.testapp.SQL.SQLHelper;

public class WelcomePage extends AppCompatActivity {
    Button btn_dn_wel,btn_dk_wel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_welcome_page);
        //let noi database
        SQLHelper sqlHelper = new SQLHelper(this);
        sqlHelper.open();
        //khai bao
        btn_dn_wel = (Button) findViewById(R.id.btn_dn_wel);
        btn_dk_wel = (Button) findViewById(R.id.btn_dk_wel);
        btn_dn_wel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this,DangNhapPage.class);
                startActivity(intent);
            }
        });
        btn_dk_wel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(WelcomePage.this,DangKyPage.class);
                startActivity(intent2);
            }
        });
    }
}