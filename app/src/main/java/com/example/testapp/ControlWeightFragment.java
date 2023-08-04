package com.example.testapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Calendar;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.DaulyFood;
import com.example.testapp.DTO.Weight;
import com.example.testapp.adapter.adapter_dailyFood;
import com.example.testapp.adapter.adapter_lichsu;
import com.example.testapp.ui.notifications.NotificationsFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ControlWeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlWeightFragment extends Fragment {
    ArrayAdapter<String> arrayAdapter;
    List<String> list = new ArrayList<>();

    String ttluu = "tkmkLog";
    private UserDataSource datasource;
    private LineChart lineChart;
    private List<String> values;
    RecyclerView recyc;
    ArrayList<Weight> weights2 = new ArrayList<>();
    adapter_lichsu adapter_lichsus;
    private int mYear, mMonth, mDay;
    Calendar calendar = Calendar.getInstance();
    Button btnDatePicker,button;
    ImageView btnPlusTY,btnMinusTY;
    ImageButton imagebtn_back2;
    TextView edt_UpdateWeight;
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    double weights ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ControlWeightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ControlWeightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlWeightFragment newInstance(String param1, String param2) {
        ControlWeightFragment fragment = new ControlWeightFragment();
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
    String Email = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=  inflater.inflate(R.layout.fragment_control_weight, container, false);
        datasource = new UserDataSource(getContext());
        datasource.open();

        //control
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct!=null){
            Email = acct.getEmail();
        }
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        lineChart = view.findViewById(R.id.idGraphView);
        btnMinusTY = view.findViewById(R.id.btnMinusTY);
        btnPlusTY = view.findViewById(R.id.btnPlusTY);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        recyc = view.findViewById(R.id.lsv_lichsu);
        edt_UpdateWeight = view.findViewById(R.id.edt_UpdateWeight);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);
        button = view.findViewById(R.id.button);
        imagebtn_back2 = view.findViewById(R.id.imagebtn_back2);
        //event
        Event();
        Weight weight = new Weight();
        weight = datasource.checkUpWeight(Email);
        double ipW = weight.getWeight();
        weights = ipW;
        edt_UpdateWeight.setText(String.valueOf(ipW));
        //
        Description description = new Description();
        description.setText("Quản lý cân nặng");
        description.setPosition(200f,15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);
        values= Arrays.asList("T1","T2","T3","T4","T5","T6","T7","T8","T9","T10","T11","T12");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(values));
        xAxis.setLabelCount(12);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);


        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0,40f));
        entries.add(new Entry(1,40f));
        entries.add(new Entry(2,40f));
        entries.add(new Entry(3,40f));
        entries.add(new Entry(4,40f));
        entries.add(new Entry(5,40f));
        entries.add(new Entry(6,40f));
        entries.add(new Entry(7,40f));
        entries.add(new Entry(8,40f));
        entries.add(new Entry(9,40f));
        entries.add(new Entry(10,40f));
        entries.add(new Entry(11,40f));


        LineDataSet dataSet2 =new LineDataSet(entries,"Maths");
        dataSet2.setColor(Color.GREEN);

        LineData lineData = new LineData(dataSet2);
        lineChart.setData(lineData);
        lineChart.invalidate();
        return view;
    }
    void UpdateWeight(String calendar){


        Weight weight = new Weight();
        weight.setControlWeight_Date(calendar);
        weight.setWeight(weightIp);
        int updateWeight = datasource.createNewWeight(weight,Email);
        if(updateWeight == 1){
            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), "Thử lại", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(ttluu, Context.MODE_PRIVATE);
        int i = sharedPreferences.getInt("weight", (int) weights);
        edt_UpdateWeight.setText(String.valueOf(i)+"kg");
    }



    double weightIp;

    void Event(){
        imagebtn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new NotificationsFragment());
            }
        });
        btnMinusTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weights -= 0.1;
                double roundedWeight = Math.round(weights * 100.0) / 100.0;
                weightIp = roundedWeight;
                edt_UpdateWeight.setText(String.valueOf(roundedWeight)+"kg");
            }
        });
        btnPlusTY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weights += 0.1;
                double roundedWeight = Math.round(weights * 100.0) / 100.0;
                weightIp = roundedWeight;
                edt_UpdateWeight.setText(String.valueOf(roundedWeight)+"kg");
                Log.e("Insert can nang ne", String.valueOf(weightIp));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(ttluu, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                UpdateWeight(currentDate);
                Weight weight1 = new Weight();
                weight1 = datasource.checkUpWeight(Email);
                double weip = weight1.getWeight();
                weights = weip;
                editor.putInt("weight", (int) weip);
                editor.apply();
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date

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
                                UpdateWeight(currentDate);

                            }

                        }, mYear, mMonth, mDay);


                datePickerDialog.show();
            }
        });
        weights2 = datasource.getAllWeightUp();
        adapter_lichsus =new adapter_lichsu(weights2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyc.setLayoutManager(layoutManager);
        recyc.setAdapter(adapter_lichsus);
    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.frg_control, fragment);
        ft.commit(); // save the changes
    }
}