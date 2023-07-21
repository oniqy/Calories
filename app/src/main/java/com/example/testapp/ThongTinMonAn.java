package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.DailyCalories;
import com.example.testapp.ui.home.HomeFragment;

public class ThongTinMonAn extends AppCompatActivity {
    private UserDataSource datasource;
    private AlertDialogSingleChoiceExample alertDialogSingleChoiceExample;
    String namFoodofDay ;
    int idFoodofDay;
    @Override
    @SuppressLint("MissingInflatedId")
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
        Button btn_addFood = (Button) findViewById(R.id.btn_addFood);
        Intent intent = getIntent();
        namFoodofDay = intent.getStringExtra("name");
        String typeOfDay = intent.getStringExtra("type");
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
        btn_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyCalories dailyCalories = new DailyCalories();
                Date currentTime = Calendar.getInstance().getTime();
                dailyCalories.setTimeofDay(typeOfDay);
                dailyCalories.setId(currentTime);
                dailyCalories.setIdFood(Integer.parseInt(intent.getStringExtra("idFood")));
                dailyCalories.setNameFoodOfday(namFoodofDay);

                //insert
                int insert = datasource.createDailyFood(dailyCalories);
                if(insert == 1){
                    Toast.makeText(getApplicationContext(), "Thêm món thành công", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Có gì đó sai sai", Toast.LENGTH_LONG).show();
                }

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