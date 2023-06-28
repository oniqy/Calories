package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DangKyPage extends AppCompatActivity {
    EditText edt_userNameViewdk,edt_pass2,edt_passViewDk;
    Button btn_ViewDk,btn_gg, btn_fb,btn_backLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dang_ky_page);
        addControl();
        addEvent();
    }
    private void  addControl(){
        edt_userNameViewdk = (EditText) findViewById(R.id.edt_userNameViewdk);
        btn_backLogin = (Button) findViewById(R.id.btn_backLogin);
    }
    private void addEvent(){
        //Quay lai trang dadng nhap
        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackLogin = new Intent(DangKyPage.this,DangNhapPage.class);
                startActivity(BackLogin);
            }
        });
    }
}