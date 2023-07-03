package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Bmi_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        PieChart pieChart = findViewById(R.id.pieChart_tyLeHu);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(55f,"Thiết yếu"));
        entries.add(new PieEntry(10f,"Giáo dục"));
        entries.add(new PieEntry(20f,"Tiết kiệm"));

        PieDataSet pieDataSet  = new PieDataSet(entries,"Jar");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData  = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(100);
        pieChart.invalidate();
    }
}