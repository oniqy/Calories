package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testapp.DAO.UserDataSource;

public class ThongTinMonAn extends AppCompatActivity {
    private UserDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_thong_tin_mon_an);
        datasource = new UserDataSource(this);
        datasource.open();
        TextView tv_nameFood = (TextView)findViewById(R.id.tv_nameFood);
        TextView tv_chatProtein = (TextView)findViewById(R.id.tv_chatProtein);
        TextView tv_chatbeo = (TextView)findViewById(R.id.tv_chatbeo);
        ImageButton imagebtn_back2 =(ImageButton)findViewById(R.id.imagebtn_back2);
        TextView tv_carbs = (TextView)findViewById(R.id.tv_carbs);
        Intent intent = getIntent();
        tv_nameFood.setText(intent.getStringExtra("name"));
        tv_chatProtein.setText(intent.getStringExtra("Proteins")+"g");
        tv_chatbeo.setText(intent.getStringExtra("Carbs")+"g");
        tv_carbs.setText(intent.getStringExtra("fats")+"g");
        imagebtn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TimMonAn.class);
                startActivity(intent);
            }
        });
    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.fmg_detailfood, fragment);
        ft.commit(); // save the changes
    }
}