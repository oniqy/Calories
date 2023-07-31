package com.example.testapp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.DailyCalories;
import com.example.testapp.DTO.DaulyFood;
import com.example.testapp.ui.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.HashSet;
import java.util.Set;
public class ThongTinMonAn extends AppCompatActivity  implements View.OnClickListener{
    private UserDataSource datasource;
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;
    private AlertDialogSingleChoiceExample alertDialogSingleChoiceExample;
    String namFoodofDay ;
    int idFoodofDay;
    String meat;
    Button btnDatePickerIndetailFood,btn_addFood;
    private int mYear, mMonth, mDay;
    Calendar calendar = Calendar.getInstance();
    DailyCalories dailyCalories = new DailyCalories();
    DaulyFood daulyFood = new DaulyFood();
    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_thong_tin_mon_an);
        datasource = new UserDataSource(this);
        datasource.open();
        showAlertDialog(ThongTinMonAn.this);

        TextView tv_nameFood = (TextView)findViewById(R.id.tv_nameFood);
        TextView tv_chatProtein = (TextView)findViewById(R.id.tv_chatProtein);
        TextView tv_chatbeo = (TextView)findViewById(R.id.tv_chatbeo);
        ImageButton imagebtn_back2 =(ImageButton)findViewById(R.id.imagebtn_back2);
        TextView tv_carbs = (TextView)findViewById(R.id.tv_carbs);
        btn_addFood = (Button) findViewById(R.id.btn_addFood);
        btn_addFood.setOnClickListener(this);
        btnDatePickerIndetailFood = findViewById(R.id.btnDatePickerIndetailFood);
        initPreferences();
        Intent intent = getIntent();
        namFoodofDay = intent.getStringExtra("name");
        tv_nameFood.setText(intent.getStringExtra("name"));
        tv_chatProtein.setText(intent.getStringExtra("Proteins")+"g");
        tv_chatbeo.setText(intent.getStringExtra("fats")+"g");
        tv_carbs.setText(intent.getStringExtra("Carbs")+"g");

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
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                daulyFood.setIdDate(currentDate);
                daulyFood.setTimeofDay(meat);
                daulyFood.setIdFood(Integer.parseInt(intent.getStringExtra("idFood")));
                daulyFood.setNameFoodOfday(namFoodofDay);
                String email = null;
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if(acct!=null){
                     email = acct.getEmail();
                }
                //insert
                int insert = datasource.createDailyFood(daulyFood,email);
                if(insert == 1){
                    Toast.makeText(getApplicationContext(), "Thêm món thành công", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Có gì đó sai sai", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnDatePickerIndetailFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date

                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThongTinMonAn.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                calendar.set(year,monthOfYear,dayOfMonth);
                                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                btnDatePickerIndetailFood.setText(currentDate);
                                daulyFood.setIdDate(currentDate);
                            }

                        }, mYear, mMonth, mDay);


                datePickerDialog.show();
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
    public void showAlertDialog(final Activity activity)  {
        String re;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Set Title.
        builder.setTitle("Chọn bữa ăn");

        // Add a list
        final String[] meats = {"Sáng", "Trưa","Tối"};

        int checkedItem = 1; // Sheep
        final Set<String> selectedItems = new HashSet<String>();
        selectedItems.add(meats[checkedItem]);

        builder.setSingleChoiceItems(meats, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do Something...
                selectedItems.clear();
                selectedItems.add(meats[which]);
            }
        });

        //
        builder.setCancelable(true);
        builder.setIcon(R.drawable.plus_icon_24);
        // Create "Yes" button with OnClickListener.
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(selectedItems.isEmpty()) {
                    return ;
                }else {
                    String meats = selectedItems.iterator().next();
                    meat = meats;
                    // Close Dialog
                    dialog.dismiss();
                    // Do something, for example: Call a method of Activity...
                    Toast.makeText(activity, "You select " + meats,
                            Toast.LENGTH_SHORT).show();

                }
            }

        });

        // Create "Cancel" button with OnClickListener.
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(activity,"You choose Cancel button",
                        Toast.LENGTH_SHORT).show();
                //  Cancel
                dialog.cancel();
            }
        });

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();

    }
    private void initPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_addFood) {
            // Nếu click vào nút Save, ta sẽ lưu dữ liệu lại.
            calendar.getTime();
            // "DATA" là key, data tham số thứ 2 là value.
            editor.putString("DATA", String.valueOf(calendar.getTime()));
            editor.commit();
        }
    }
}