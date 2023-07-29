package com.example.testapp.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.DailyCalories;
import com.example.testapp.DTO.DaulyFood;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.ImageLoadTask;
import com.example.testapp.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.ThongTinMonAn;
import com.example.testapp.TimMonAn;
import com.example.testapp.adapter.adapter_dailyFood;
import com.example.testapp.databinding.FragmentHomeBinding;
import com.example.testapp.ui.notifications.BMR_page_Fragment;
import com.example.testapp.ui.notifications.Input_Info_Fragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private UserDataSource datasource;
    private ThongTinMonAn thongTinMonAn;
    GoogleSignInOptions gso;
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;
    GoogleSignInClient gsc;
    RecyclerView recyc;
    ArrayList<DaulyFood> daulyFoods = new ArrayList<>();
    adapter_dailyFood adapter_dailyFoods;
    ImageView userImg;
    List<String> list = new ArrayList<>();
    TextView userHello,chiSoCalo,userDate,numb_caloIn;
    BMR_page_Fragment bmr_page_fragment;
    private FragmentHomeBinding binding;
    DailyCalories dailyCalories ;
    String getType ;
    ProgressBar progressBarCalo;
    Button btnDatePicker;
    private int mYear, mMonth, mDay;
    Calendar currentDate = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        datasource = new UserDataSource(getContext());
        datasource.open();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        addControl();
        addEnvent();
        singUpGG();
        initPreferences();
        currentDate();
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        btnDatePicker.setText(currentDate);
        showCalories();



//
        return root;
    }
    private Date currentDate(){
        Calendar calendarcheck = Calendar.getInstance();
        return calendarcheck.getTime();
    }
    private void showCalories(){


        double tdee = 0;
        double bmr = tinhBMR();
        if(bmr == -1){
            Toast.makeText(getContext(),"Vui lòng thiết lập chỉ số BMR",Toast.LENGTH_LONG).show();
        }else {
            double r = tinhTDEE();
            tdee = bmr * r;
            double target = tinhTarget();

            tdee = tdee + target;
            if(tdee < bmr){
                tdee =bmr+65;
            }
            binding.chiSoCalo.setText(String.format(Locale.US, "%.0f", tdee) + "\n/Kcal");
        }
        int caloriesIn = datasource.Tinhcalo();
        if(caloriesIn == -1){
            numb_caloIn.setText(Integer.toString(0));
            progressBarCalo.setProgress(0);
        }else {
            progressBarCalo.setMax((int) tdee);
            double caloriesConLai = tdee - caloriesIn;
            double caloriesInDay = tdee - caloriesConLai;
            numb_caloIn.setText(Integer.toString(caloriesIn));
            progressBarCalo.setProgress((int) caloriesInDay);
        }

    }
    private void singUpGG(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct!=null){
            String personName = acct.getDisplayName();
            Uri  uri= acct.getPhotoUrl();

            if (uri == null){
                new ImageLoadTask("https://icons.veryicon.com/png/o/miscellaneous/two-color-icon-library/user-286.png",userImg).execute();
            }
            else {
                userHello.setText("Hi," + personName);
                new ImageLoadTask(uri.toString(), userImg).execute();
            }
        }
    }
    private void addControl(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getContext(),gso);
        userImg = binding.userImg.findViewById(R.id.userImg);
        progressBarCalo = binding.progressBarCalo.findViewById(R.id.progressBarCalo);
        numb_caloIn = binding.numbCaloIn.findViewById(R.id.numb_caloIn);
        LinearLayout layoutbuoiSang =  binding.layoutbuoiSang.findViewById(R.id.layoutbuoiSang);
        userHello = binding.userHello.findViewById(R.id.userHello);
        btnDatePicker = binding.btnDatePicker.findViewById(R.id.btnDatePicker);
    }
    private void addEnvent(){
        binding.btnVEdtDailyFoob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  i = datasource.createFood();
                if(i == -1){

                }else {
                    Intent intent = new Intent(getContext(),TimMonAn.class);
                    startActivity(intent);
                }

            }
        });
        binding.textNotificationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Input_Info_Fragment());

            }
        });

        binding.imgbtnBs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyCalories dailyCalories = new DailyCalories();

                dailyCalories.setTimeofDay("Sang");
                int  i = datasource.createFood();
                if(i == -1){

                }else {
                    Intent intent = new Intent(getContext(),TimMonAn.class);
                    startActivity(intent);
                }


            }
        });
        binding.imgbtnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyCalories dailyCalories1 = new DailyCalories();
                dailyCalories1.setTimeofDay("Trua");


            }
        });
        binding.imgbtnBtoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int  i = datasource.createFood();
                if(i == -1){

                }else {
                    Intent intent = new Intent(getContext(),TimMonAn.class);
                    startActivity(intent);
                }


            }
        });
        binding.layoutbuoiSang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyc =(RecyclerView) binding.lsvSang.findViewById(R.id.lsv_sang);
                daulyFoods = datasource.getFood("Sáng");

                adapter_dailyFoods =new adapter_dailyFood(daulyFoods);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyc.setLayoutManager(layoutManager);
                recyc.setAdapter(adapter_dailyFoods);
                adapter_dailyFoods.setOnItemClick(new adapter_dailyFood.OnItemClick() {
                    @Override
                    public void onIttemClick(int position) {
                        String date = daulyFoods.get(position).getIdDate();
                        String savedData = sharedPreferences.getString("DATA", "");
                        int delete = datasource.deteleFood(date);
                        if (delete == 0){
                            daulyFoods.remove(position);
                        }
                    }
                });

            }
        });

        binding.layoutBuoitrua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyc =(RecyclerView) binding.lsvTrua.findViewById(R.id.lsv_trua);
                daulyFoods = datasource.getFood("Trưa");
                adapter_dailyFoods =new adapter_dailyFood(daulyFoods);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyc.setLayoutManager(layoutManager);
                recyc.setAdapter(adapter_dailyFoods);
                adapter_dailyFoods.setOnItemClick(new adapter_dailyFood.OnItemClick() {
                    @Override
                    public void onIttemClick(int position) {
                        String date = daulyFoods.get(position).getIdDate();
                        String savedData = sharedPreferences.getString("DATA", "");
                        int delete = datasource.deteleFood(date);
                        if (delete == 0){
                            daulyFoods.remove(position);
                        }
                    }
                });

            }
        });
        binding.layoutBuoiToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyc =(RecyclerView) binding.lsvToi.findViewById(R.id.lsv_toi);
                daulyFoods = datasource.getFood("Tối");
                adapter_dailyFoods =new adapter_dailyFood(daulyFoods);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyc.setLayoutManager(layoutManager);
                recyc.setAdapter(adapter_dailyFoods);
                adapter_dailyFoods.setOnItemClick(new adapter_dailyFood.OnItemClick() {
                    @Override
                    public void onIttemClick(int position) {
                        String date = daulyFoods.get(position).getIdDate();
                        String savedData = sharedPreferences.getString("DATA", "");
                        int delete = datasource.deteleFood(date);
                        if (delete == 0){
                            daulyFoods.remove(position);
                        }
                    }
                });
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,0);
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                calendar.set(year,monthOfYear,dayOfMonth);
                                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                btnDatePicker.setText(currentDate);
                                showCalories();
                            }

                        }, mYear, mMonth, mDay);


                datePickerDialog.show();
            }
        });

    }
    public double tinhBMR(){
        UserInfo userInfo = datasource.Bmr();

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
        UserInfo userInfo = datasource.Bmr();
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
        UserInfo userInfo = datasource.Bmr();
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
    private void initPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();
    }

    @Override
    public void onResume() {
        super.onResume();
        showCalories();
    }

    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.fmg_homme, fragment);
        ft.commit(); // save the changes
    }

}