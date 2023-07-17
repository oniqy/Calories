package com.example.testapp.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.R;
import com.example.testapp.databinding.FragmentBMRPageBinding;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BMR_page_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BMR_page_Fragment extends Fragment {

    private UserDataSource datasource;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentBMRPageBinding binding;
    public BMR_page_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BMR_page_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BMR_page_Fragment newInstance(String param1, String param2) {
        BMR_page_Fragment fragment = new BMR_page_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBMRPageBinding.inflate(inflater, container, false);
        datasource = new UserDataSource(getContext());
        datasource.open();
        binding.btnTinhBMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               double bmr = tinhBMR();
               if(bmr == -1){
                   Toast.makeText(getContext(),"Vui lòng thiết lập chỉ số BMR",Toast.LENGTH_LONG).show();
               }else {
                   binding.textViewTylebmr.setText(String.format(Locale.US, "%.0f", bmr)+" Kcal/day");
               }

            }
        });
        binding.btnTinhTDEE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double bmr = tinhBMR();
                if(bmr == -1){
                    Toast.makeText(getContext(),"Vui lòng thiết lập chỉ số BMR",Toast.LENGTH_LONG).show();
                }else {
                    double r = tinhTDEE();
                    double tdee = bmr * r;

                    binding.textViewTyle.setText(String.format(Locale.US, "%.0f", tdee) + " Kcal/day");
                }
            }
        });
        binding.btnTinhTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double bmr = tinhBMR();
                if(bmr == -1){
                    Toast.makeText(getContext(),"Vui lòng thiết lập chỉ số BMR",Toast.LENGTH_LONG).show();
                }else {
                    double r = tinhTDEE();
                    double tdee = bmr * r;
                    double target = tinhTarget();

                    tdee = tdee + target;
                    if(tdee < bmr){
                        tdee =bmr+65;
                    }
                    binding.textViewTyletarget.setText(String.format(Locale.US, "%.0f", tdee) + " Kcal/day");
                }
            }
        });
        binding.imagebtnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new NotificationsFragment());
            }
        });
        binding.textNotificationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Input_Info_Fragment());
            }
        });
        // Inflate the layout for this fragment
        View root = binding.getRoot();
        return root;
    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.fragment_BMR, fragment);
        ft.commit(); // save the changes
    }
}
