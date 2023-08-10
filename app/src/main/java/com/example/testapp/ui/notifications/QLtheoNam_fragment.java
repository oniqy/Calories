package com.example.testapp.ui.notifications;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.icu.text.DateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;
import java.text.DecimalFormat;
import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.DaulyFood;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.R;
import com.example.testapp.adapter.adapter_lichSuCalo;
import com.example.testapp.adapter.adapter_lichsu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.example.testapp.ui.home.ChartCurrencyFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLtheoNam_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLtheoNam_fragment extends Fragment {
    BarChart mChart;
    SharedPreferences sharedPreferences;
    private UserDataSource datasource;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    View view;
    ImageButton imagebtn_back2;
    private String mParam1;
    RecyclerView recyc;
    String email= null;
    String getType ;
    private String mParam2;
    Button btnDatePikerWeek;
    adapter_lichSuCalo adapter_lichSuCalos;
    ArrayList<DaulyFood> daulyFood = new ArrayList<DaulyFood>();
    SwipeRefreshLayout swipeRefreshLayout;
    private int mYear, mMonth, mDay,selection=0;
    Calendar currentDate = Calendar.getInstance();
    public QLtheoNam_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLtheoNam_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLtheoNam_fragment newInstance(String param1, String param2) {
        QLtheoNam_fragment fragment = new QLtheoNam_fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calories__line__chart_, container, false);
        datasource = new UserDataSource(getContext());
        datasource.open();
        btnDatePikerWeek = view.findViewById(R.id.btnDatePikerWeek);
        recyc = view.findViewById(R.id.lsv_lichsu);
        imagebtn_back2 = view.findViewById(R.id.imagebtn_back2);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct!=null){
            email = acct.getEmail();
        };
        getType=DateFormat.getDateInstance(DateFormat.FULL).format(currentDate.getTime());
        showHistory(email,getType);
        showTieuHao(getType);
        GroupBarChart(getType);
        imagebtn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new NotificationsFragment());
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GroupBarChart(getType);
                adapter_lichSuCalos =new adapter_lichSuCalo(daulyFood);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyc.setLayoutManager(layoutManager);
                recyc.setAdapter(adapter_lichSuCalos);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        btnDatePikerWeek.setOnClickListener(new View.OnClickListener() {
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
                                String currentDate2 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                getType=DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                GroupBarChart(getType);
                                showHistory(email,getType);
                                showTieuHao(getType);
                            }

                        }, mYear, mMonth, mDay);


                datePickerDialog.show();
            }
        });

        return view;
    }
    double soCaloTieuhao = 0;
    public void showTieuHao(String type){
        DecimalFormat df = new DecimalFormat("#.#");
        Context context = getContext();
        sharedPreferences = context.getSharedPreferences(type, Context.MODE_PRIVATE);
        soCaloTieuhao = sharedPreferences.getFloat(type,0);
    }
    public void showHistory(String em , String type){
        daulyFood = datasource.getFoodShowhistoRydata(email,getType);
        adapter_lichSuCalos =new adapter_lichSuCalo(daulyFood);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyc.setLayoutManager(layoutManager);
        recyc.setAdapter(adapter_lichSuCalos);
    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = requireActivity().getSupportFragmentManager();

        // Create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(androidx.transition.R.anim.fragment_open_exit, androidx.transition.R.anim.fragment_close_exit);
// replace the FrameLayout with new Fragment
        ft.replace(R.id.frg_bcct, fragment);
        ft.commit(); // save the changes
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

    @Override
    public void onResume() {
        showTieuHao(getType);
        super.onResume();
    }

    public void GroupBarChart(String geType){
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
        }
        DecimalFormat df = new DecimalFormat("#");
        mChart = (BarChart) view.findViewById(R.id.mChart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(true);

        // empty labels so that the names are spread evenly
        String[] labels = {geType};
        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setAxisMinimum(1f);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(8, true);

        leftAxis.setAxisMinimum(0f);  // Set the minimum value
        leftAxis.setGranularity(1f);  // Set the interval between axis values
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMaximum(Float.parseFloat(df.format(tdee)));

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);
        int caloriesIn = datasource.Tinhcalo(email,geType);

        float[] valCaloin = {caloriesIn};
        float[] valCaloOut = {(float) soCaloTieuhao};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        ;
        for (int i = 0; i < valCaloin.length; i++) {
            barOne.add(new BarEntry(i, valCaloin[i]));
            barTwo.add(new BarEntry(i, valCaloOut[i]));

        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(Color.parseColor("#2ecc71"));
        set1.setDrawValues(true);
        set1.setValueFormatter(new ChartCurrencyFormatter());
        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
        set2.setColor(Color.parseColor("#e74c3c"));
        set2.setDrawValues(true);
        set2.setValueFormatter(new ChartCurrencyFormatter());


        set1.setHighlightEnabled(true);
        set2.setHighlightEnabled(true);



        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        float groupSpace = 0.4f;
        float barSpace = 0f;
        float barWidth = 0.3f;
        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        data.setValueTextColor(Color.parseColor("#FFFFFFFF"));
        data.setValueTextSize(10);
        LimitLine limitLine = new LimitLine((float) tdee, ""+Float.parseFloat(df.format(tdee)));
        limitLine.setLineColor(Color.GREEN);
        limitLine.setLineWidth(5f);
        limitLine.setTextColor(Color.BLACK);
        limitLine.setTextSize(12f);

        leftAxis.addLimitLine(limitLine);

        leftAxis.setAxisMaximum((float) tdee + 414f);
        for (int i = 0; i< leftAxis.mEntries.length; i++){
            leftAxis.mEntries[i] = Math.round(leftAxis.mEntries[i]);
        }
        leftAxis.setValueFormatter(new ChartCurrencyFormatter());
        mChart.setData(data);
        mChart.setScaleEnabled(false);
        mChart.setDrawValueAboveBar(false);
        mChart.setVisibleXRangeMaximum(6f);

        mChart.setHighlightFullBarEnabled(true);
        mChart.groupBars(1f, groupSpace, barSpace);
        mChart.animateXY(1000, 1000);
        mChart.getLegend().setEnabled(false);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setTouchEnabled(true);

        mChart.setDrawGridBackground(false); // Disable drawing the grid background

        mChart.invalidate();
        mChart.animateY(1000); // Vertical animation
        mChart.animateX(1000);

    }
}