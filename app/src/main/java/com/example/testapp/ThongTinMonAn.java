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

import android.animation.ObjectAnimator;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.DailyCalories;
import com.example.testapp.DTO.DaulyFood;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.ui.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.HashSet;
import java.util.Set;
import java.text.DecimalFormat;
public class ThongTinMonAn extends AppCompatActivity  implements View.OnClickListener{
    private UserDataSource datasource;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar5,progressBar6,progressBar7;
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
    String email= null;
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
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(acct!=null){
            email = acct.getEmail();
        };
        TextView tv_nameFood = (TextView)findViewById(R.id.tv_nameFood);
        TextView tv_chatProtein = (TextView)findViewById(R.id.tv_chatProtein);
        TextView tv_chatbeo = (TextView)findViewById(R.id.tv_chatbeo);
        ImageButton imagebtn_back2 =(ImageButton)findViewById(R.id.imagebtn_back2);
        TextView tv_carbs = (TextView)findViewById(R.id.tv_carbs);
        progressBar5 = (ProgressBar)findViewById(R.id.progressBar5);
        progressBar6 = (ProgressBar)findViewById(R.id.progressBar6);
        progressBar7 = (ProgressBar)findViewById(R.id.progressBar7);

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
        DecimalFormat df = new DecimalFormat("#");
        double tdee = 0;
        double bmr = tinhBMR();
        if(bmr == -1){
            Toast.makeText(getApplicationContext(),"Vui lòng thiết lập chỉ số BMR",Toast.LENGTH_LONG).show();
        }else {
            double r = tinhTDEE();
            tdee = bmr * r;
            double target = tinhTarget();

            tdee = tdee + target;
            if(tdee < bmr){
                tdee =bmr+65;
            }
        }
        double proteins = tdee*0.35/4;
        double fats = tdee*0.3/9;
        double carb = tdee*0.35/4;
        progressBar5.setMax((int) proteins);
        progressBar6.setMax((int) fats);
        progressBar7.setMax((int) carb);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar5,
                "progress", 0, Integer.parseInt(intent.getStringExtra("Proteins")));
        progressAnimator.setDuration(2000);
        progressAnimator.start();
        ObjectAnimator progressAnimator2 = ObjectAnimator.ofInt(progressBar6,
                "progress", 0, Integer.parseInt(intent.getStringExtra("fats")));
        progressAnimator2.setDuration(2000);
        progressAnimator2.start();
        ObjectAnimator progressAnimator3 = ObjectAnimator.ofInt(progressBar7,
                "progress", 0, Integer.parseInt(intent.getStringExtra("Carbs")));
        progressAnimator3.setDuration(2000);
        progressAnimator3.start();
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
                //GGEmail
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if(acct!=null){
                     email = acct.getEmail();
                }
                //
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
    public double tinhBMR(){
        UserInfo userInfo = datasource.Bmr(email);

        String sex = userInfo.getGender();
        if(sex == null){
            return -1;
        }
        int chieuCao = userInfo.getUserHeight();
        int canNang = userInfo.getUserWeight();
        int age = userInfo.getBirthDay();
        double BMR ;
        if(sex == "Nam"){
            double BMR1 = 88.362+(13.397*canNang)+(4.799*chieuCao);
            BMR = BMR1 - (5.677 * age);

        }else {
            double BMR1 = 447.593 +(9.247 *canNang)+(3.098 *chieuCao);
            BMR = BMR1 - (4.33  * age);
        }
        return BMR;
    }
    public double tinhTDEE(){
        UserInfo userInfo = datasource.Bmr(email);
        String exercise = userInfo.getExercise();
        double R = 0;
        if(exercise.equals("Không tập")){
            R = 1.2;
        } else if (exercise.equals("Nhẹ nhàng")) {
            R = 1.375;
        }
        else if (exercise.equals("Vừa phải")) {
            R = 1.55;
        }
        else if (exercise.equals("Nặng")) {
            R = 1.725;
        }
        return R;
    }
    public double tinhTarget(){
        UserInfo userInfo = datasource.Bmr(email);
        String exercise = userInfo.getTarget();
        double R = 0;
        if(exercise.equals("Giảm cân")){
            R = -500;
        } else if (exercise.equals("Giữ nguyên cân nặng")) {
            R = 0;
        }
        else if (exercise.equals("Tăng cân")) {
            R = 500;
        }
        return R;
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
                    Toast.makeText(activity, "Món ăn này dành cho buổi " + meats,
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