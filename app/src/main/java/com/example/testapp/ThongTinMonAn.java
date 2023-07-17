package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ThongTinMonAn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_thong_tin_mon_an);
        TextView tv_nameFood = (TextView)findViewById(R.id.tv_nameFood);
        TextView tv_chatProtein = (TextView)findViewById(R.id.tv_chatProtein);
        TextView tv_chatbeo = (TextView)findViewById(R.id.tv_chatbeo);
        TextView tv_carbs = (TextView)findViewById(R.id.tv_carbs);
        Intent intent = getIntent();
        tv_nameFood.setText(intent.getStringExtra("name"));
        tv_chatProtein.setText(intent.getStringExtra("Proteins"));
        tv_chatbeo.setText(intent.getStringExtra("Carbs"));
        tv_carbs.setText(intent.getStringExtra("fats"));
    }
}