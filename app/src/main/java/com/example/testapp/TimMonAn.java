package com.example.testapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.DailyCalories;
import com.example.testapp.DTO.FoodMenu;
import com.example.testapp.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class TimMonAn extends Activity {
    private UserDataSource datasource;
    ArrayAdapter<String> arrayAdapter;
    List<String> list = new ArrayList<>();
    ListView lsV;
    private AlertDialogSingleChoiceExample alertDialogSingleChoiceExample;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_mon_an);
        datasource = new UserDataSource(this);
        datasource.open();

        lsV = (ListView) findViewById(R.id.lsV);
        ImageButton btnV_search = (ImageButton)findViewById(R.id.btnV_search);
        ImageButton imagebtn_back2 = (ImageButton)findViewById(R.id.imagebtn_back2);
        EditText edtTimKiemFoob= (EditText)findViewById(R.id.edtTimKiemFoob);
        list.clear();
        list = datasource.getAllfood();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        lsV.setAdapter(arrayAdapter);
        imagebtn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btnV_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFood = edtTimKiemFoob.getText().toString();
                list.clear();
                list = datasource.timKiemfood(nameFood);
                arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,list);
                lsV.setAdapter(arrayAdapter);


            }
        });
        lsV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodMenu foodMenu = datasource.detail_food(position);
                DailyCalories dailyCalories = new DailyCalories();
                if(foodMenu == null){
                    Toast.makeText(getApplicationContext(),"Lỗi gì đó",Toast.LENGTH_LONG).show();
                }else {
                    String day = dailyCalories.getNameFoodOfday();
                    String idFood = String.valueOf(foodMenu.getIdFood());
                    String nameFoob = foodMenu.getFoodName();
                    String Proteins = String.valueOf(foodMenu.getProteins());
                    String Carbs = String.valueOf(foodMenu.getCarbs());
                    String fats = String.valueOf(foodMenu.getFats());
                    Intent intent = new Intent(getApplicationContext(), ThongTinMonAn.class);
                    intent.putExtra("idFood",idFood);
                    intent.putExtra("name",nameFoob);
                    intent.putExtra("Proteins",Proteins);
                    intent.putExtra("Carbs",Carbs);
                    intent.putExtra("fats",fats);
                    startActivity(intent);
                }
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list = datasource.getAllfood();
        arrayAdapter = new ArrayAdapter<>(TimMonAn.this, android.R.layout.simple_list_item_1,list);
        lsV.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}