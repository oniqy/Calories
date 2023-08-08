package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.ui.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class tonghopbaitap extends AppCompatActivity implements SensorEventListener {
    TextView stepcounter, caloStep;
    SensorManager sensorManager;
    Sensor msensor;
    boolean isCounterSensorPresent; //cái này để check xem máy mình có sensor chuyển động k

    ImageButton imgBtn_baitap_back;

    private UserDataSource datasource;

    String email = null;

    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

    int stepCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tonghopbaitap);
        getSupportActionBar().hide();

        addControls();
        addEvents();
        stepcounter = (TextView) findViewById(R.id.stepcounter);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            msensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }else {
            stepcounter.setText("-10000");
            isCounterSensorPresent = false;
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == msensor.TYPE_STEP_COUNTER){
            stepCount = (int) event.values[0];
            stepcounter.setText(String.valueOf(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.registerListener(this, msensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.unregisterListener(this, msensor );
        }
    }

    //==============================================================================
    private void addControls(){
        imgBtn_baitap_back  = (ImageButton) findViewById(R.id.imgBtn_baitap_back);
        caloStep = (TextView) findViewById(R.id.caloStep);

    }

    private void addEvents(){
        imgBtn_baitap_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //?????????????????????
//        if(acct!=null){
//            email = acct.getEmail();
//        };
//
//        UserInfo userInfo = datasource.Bmr(email);
//        int kg = userInfo.getUserWeight();
//        double stepcalo = (kg * 1.09) * Integer.parseInt(stepcounter.getText().toString());
//
//        caloStep.setText(String.valueOf(stepcalo));

    }


}