package com.example.testapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


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
import com.example.testapp.ui.notifications.Input_Info_Fragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class DangKyPage extends AppCompatActivity implements View.OnClickListener{
    EditText edt_userNameViewdk,edt_pass2,edt_passViewDk;
    Button btn_ViewDk,btn_gg, btn_fb,btn_backLogin;
    private long acc;
    private UserDataSource datasource;
    GoogleSignInOptions signIn;
    GoogleSignInClient signInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dang_ky_page);
        datasource = new UserDataSource(this);
        datasource.open();
        addControl();
        signIn = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        signInClient = GoogleSignIn.getClient(this,signIn);
        btn_gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    void  signIn(){
        Intent signIntent = signInClient.getSignInIntent();
        startActivityForResult(signIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                int check = datasource.checkUserInfo();
                if(check == 1){
                    loadFragment(new Input_Info_Fragment());
                    Toast.makeText(this,"Hãy thiết lập chỉ số BMR của bạn trước khi sử dụng HealthyCare",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),"Co gi do sai sai",Toast.LENGTH_LONG).show();
            }
        }
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
            String password = edt_passViewDk.getText().toString();
            String pw2 = edt_pass2.getText().toString();
            int result;
            result = password.compareTo(pw2);
            boolean isAtLeast8   = password.length() >= 6;
            boolean hasUppercase = !password.equals(password.toLowerCase());
            if(result == 0){
                if (hasUppercase == true && isAtLeast8 == true) {
                    userAcc.setUsername(usm);
                    userAcc.setPassWord(password);
                    int k = datasource.createUserACc(userAcc);
                    if (k == 1) {
                        Toast.makeText(DangKyPage.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                        Intent BackLogin = new Intent(DangKyPage.this, DangNhapPage.class);
                        startActivity(BackLogin);
                    } else if (k == -1) {
                        Toast.makeText(DangKyPage.this, "Tên đăng nhập không hợp lệ!", Toast.LENGTH_LONG).show();
                    }
                }
                else  {
                    Toast.makeText(DangKyPage.this, "Mật khẩu phải có ít nhất 8 ký tự và 1 chữ cái in hoa", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(DangKyPage.this, "Mật khẩu không trùng khớp", Toast.LENGTH_LONG).show();

            }
        }
        if (v.getId() == R.id.btn_backLogin) {
            Intent BackLogin = new Intent(DangKyPage.this, DangNhapPage.class);
            startActivity(BackLogin);
        }
        if (v.getId() == R.id.btn_gg) {

        }


    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.fmg_dangky, fragment);
        ft.commit(); // save the changes
    }
}