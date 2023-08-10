package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.UserAcc;
import com.example.testapp.ui.notifications.Input_Info_Fragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class DangNhapPage extends AppCompatActivity implements View.OnClickListener{
    Button btn_dk,btn_dn;
    ImageButton imgBtn_signupGG;
    EditText edt_userName,edt_pass;
    UserAcc userAcc;
    GoogleSignInOptions signIn;
    GoogleSignInClient signInClient;
    TextView tv_dangKy;
    private UserDataSource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dang_nhap_page);
        datasource = new UserDataSource(this);
        datasource.open();
        signIn = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("237423378678-ajagq0kuaj94t6sokofnhc0poa80tqrq.apps.googleusercontent.com").requestEmail().build();
        signInClient = GoogleSignIn.getClient(this,signIn);
        addControl();
        imgBtn_signupGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity
            startActivity(new Intent(DangNhapPage.this, WelcomePage.class));
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(DangNhapPage.this);
                if(acct!=null){
                    String personEmail = acct.getEmail();
                    String personid = acct.getId();
                    UserAcc userAcc = new UserAcc();
                    userAcc.setUsername(personEmail);
                    userAcc.setPassWord(personid);
                    int check = datasource.checkUserAcc(personEmail);

                    if(check == 1) {
                        int k = datasource.createUserACc(userAcc);
                        if (k == 1) {
                            loadFragment(new Input_Info_Fragment());

                            Toast.makeText(this, "Hãy thiết lập chỉ số BMR của bạn trước khi sử dụng HealthyCare", Toast.LENGTH_LONG).show();
                        } else if (k == -1) {
                            Toast.makeText(DangNhapPage.this, "Tên đăng nhập không hợp lệ!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Intent intent = new Intent(DangNhapPage.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),"Co gi do sai sai",Toast.LENGTH_LONG).show();
                Log.v("AKi LOGIN EXCEPTION", e.getMessage().toString());
            }
        }
    }
    void  signIn(){
        Intent signIntent = signInClient.getSignInIntent();
        startActivityForResult(signIntent,1000);
    }
    private void addControl(){
        imgBtn_signupGG = (ImageButton)findViewById(R.id.imgBtn_signupGG);
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


    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.frag_Infro, fragment);
        ft.commit(); // save the changes
    }
}