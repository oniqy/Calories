package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.UserAcc;
import com.example.testapp.ui.notifications.Input_Info_Fragment;

public class DangNhapPage extends AppCompatActivity implements View.OnClickListener{
    Button btn_dk,btn_dn;
    EditText edt_userName,edt_pass;
    UserAcc userAcc;
    private UserDataSource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dang_nhap_page);
        datasource = new UserDataSource(this);
        datasource.open();

        addControl();
    }
    private void addControl(){
        btn_dk = (Button) findViewById(R.id.btn_dk);
        btn_dn=(Button) findViewById(R.id.btn_dn);
        edt_userName = (EditText) findViewById(R.id.edt_userName);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btn_dk.setOnClickListener(this::onClick);
        btn_dn.setOnClickListener(this::onClick);
        btn_dk.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void CheckLogin(){
        String userName = edt_userName.getText().toString();
        String pw = edt_pass.getText().toString();
        int ktr = datasource.CheckLogin(userName,pw);
        if(ktr == 1){
            Toast.makeText(DangNhapPage.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(DangNhapPage.this,MainActivity.class);
            startActivity(intent);
        }else if(ktr == 2) {
            Toast.makeText(DangNhapPage.this, "Sai mật khẩu!!", Toast.LENGTH_LONG).show();
        }else {
            btn_dn.setVisibility(View.GONE);
            btn_dk.setVisibility(View.VISIBLE);
            Toast.makeText(DangNhapPage.this, "Bạn chưa có tài khoản!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_dn) {
            CheckLogin();
        }
        if (v.getId()== R.id.btn_dk){
            Intent intent = new Intent(DangNhapPage.this,DangKyPage.class);
            startActivity(intent);
        }
    }
}