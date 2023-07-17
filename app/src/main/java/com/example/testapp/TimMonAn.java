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
import com.example.testapp.DTO.FoodMenu;
import com.example.testapp.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class TimMonAn extends Activity {
    private UserDataSource datasource;
    ArrayAdapter<String> arrayAdapter;
    List<String> list = new ArrayList<>();
    ListView lsV;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_mon_an);
        datasource = new UserDataSource(this);
        datasource.open();

        lsV = (ListView) findViewById(R.id.lsV);
        ImageButton btnV_search = (ImageButton) findViewById(R.id.btnV_search);
        Button btn_loafmenu = (Button) findViewById(R.id.btn_loafmenu);
        ImageButton imagebtn_back2 = (ImageButton)findViewById(R.id.imagebtn_back2);
        EditText edtTimKiemFoob= (EditText)findViewById(R.id.edtTimKiemFoob);

        btn_loafmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = datasource.createFood();
                if(i == -1){
                    Toast.makeText(TimMonAn.this,"Lỗi gì đó",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(TimMonAn.this,"Loading",Toast.LENGTH_LONG).show();
                    list.clear();
                    list = datasource.getAllfood();
                    arrayAdapter = new ArrayAdapter<>(TimMonAn.this, android.R.layout.simple_list_item_1,list);
                    lsV.setAdapter(arrayAdapter);
                }
            }
        });
        btnV_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFood = edtTimKiemFoob.getText().toString();
                list.clear();
                list = datasource.timKiemfood(nameFood);
                arrayAdapter = new ArrayAdapter<>(TimMonAn.this, android.R.layout.simple_list_item_1,list);
                lsV.setAdapter(arrayAdapter);

            }
        });
        lsV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodMenu foodMenu = datasource.detail_food(position);
                if(foodMenu == null){
                    Toast.makeText(TimMonAn.this,"Lỗi gì đó",Toast.LENGTH_LONG).show();
                }else {
                    String nameFoob = foodMenu.getFoodName();
                    int Proteins = foodMenu.getProteins();
                    int Carbs = foodMenu.getCarbs();
                    int fats = foodMenu.getFats();
                    Intent intent = new Intent(TimMonAn.this, ThongTinMonAn.class);
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
    protected void onResume() {
        super.onResume();

    }
}