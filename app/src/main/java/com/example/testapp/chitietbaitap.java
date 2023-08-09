package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testapp.DTO.LuuCaloTieuHao;

import java.util.Locale;
import java.text.DecimalFormat;
public class chitietbaitap extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int seconds = 0;
    ImageButton imgBtn_chitietbaitap_back;
    private boolean running;
    private boolean wasRunning;
    TextView tv_showCalo,tv_NameTapluyen,tv_tongcalo;
    double soCalo = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietbaitap);
        getSupportActionBar().hide();
        tv_showCalo = (TextView) findViewById(R.id.tv_showCalo);
        tv_NameTapluyen = (TextView) findViewById(R.id.tv_NameTapluyen);
        tv_tongcalo = (TextView) findViewById(R.id.tv_tongcalo);
        imgBtn_chitietbaitap_back = (ImageButton) findViewById(R.id.imgBtn_chitietbaitap_back);

        Intent intent = getIntent();
        soCalo = intent.getDoubleExtra("calo",0);
        tv_NameTapluyen.setText(intent.getStringExtra("name"));
        tv_showCalo.setText(String.valueOf(soCalo));

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //====================================
        imgBtn_chitietbaitap_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //====================================
        if (savedInstanceState != null) {

            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();
    }


    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
        DecimalFormat df = new DecimalFormat("#.#");
        double tongCalo = soCalo * seconds;
        tv_tongcalo.setText(String.valueOf(df.format(tongCalo)));
        editor.putFloat("tongcalo", Float.parseFloat(df.format(tongCalo)));
        editor.apply();
    }


    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {

        final TextView timeView = (TextView) findViewById(R.id.time_view);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}