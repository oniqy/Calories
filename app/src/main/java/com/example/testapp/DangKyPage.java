package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.UserAcc;
import com.example.testapp.ui.home.HomeFragment;

public class DangKyPage extends AppCompatActivity implements View.OnClickListener{
    EditText edt_userNameViewdk,edt_pass2,edt_passViewDk;
    Button btn_ViewDk,btn_gg, btn_fb,btn_backLogin;
    private long acc;
    private UserDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dang_ky_page);
        datasource = new UserDataSource(this);
        datasource.open();
        addControl();
    }
    private void  addControl(){
        edt_userNameViewdk = (EditText) findViewById(R.id.edt_userNameViewdk);
        edt_passViewDk = (EditText) findViewById(R.id.edt_passViewDk);
        edt_pass2 = (EditText) findViewById(R.id.edt_pass2);
        btn_backLogin = (Button) findViewById(R.id.btn_backLogin);
        btn_ViewDk = (Button) findViewById(R.id.btn_ViewDk);
        btn_gg = (Button) findViewById(R.id.btn_gg);
        btn_ViewDk.setOnClickListener(this::onClick);
        btn_backLogin.setOnClickListener(this::onClick);
        btn_gg.setOnClickListener(this::onClick);



    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ViewDk) {
            UserAcc userAcc = new UserAcc();
            String usm = edt_userNameViewdk.getText().toString();
            String pw = edt_passViewDk.getText().toString();
            String pw2 = edt_pass2.getText().toString();
            if (pw.equals(pw2)) {
                userAcc.setUsername(usm);
                userAcc.setPassWord(pw);
                int k = datasource.createUser(userAcc);
                if (k == 1) {
                    Toast.makeText(DangKyPage.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DangKyPage.this, HomeFragment.class);
                    startActivity(intent);
                } else if (k == -1) {
                    Toast.makeText(DangKyPage.this, "Tên đăng nhập không hợp lệ!", Toast.LENGTH_LONG).show();
                }
            }
            else  {
                Toast.makeText(DangKyPage.this, "Nhập lại mật khẩu", Toast.LENGTH_LONG).show();
            }
        }
        if (v.getId() == R.id.btn_backLogin) {
            Intent BackLogin = new Intent(DangKyPage.this, DangNhapPage.class);
            startActivity(BackLogin);
        }
        if (v.getId() == R.id.btn_gg) {

        }


    }
}