package com.example.testapp;
import java.util.Calendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.FoodMenu;
import com.example.testapp.DTO.LuuCaloTieuHao;
import com.example.testapp.DTO.TapLuyen;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.ui.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.DecimalFormat;
public class tonghopbaitap extends AppCompatActivity implements SensorEventListener {
    TextView stepcounter, caloStep, totalCalo;
    SensorManager sensorManager;
    List<String> list = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Sensor msensor;
    String ttluu = "tkmkLog";
    boolean isCounterSensorPresent; //cái này để check xem máy mình có sensor chuyển động k
    SharedPreferences.Editor editor;
    ListView lv_Baitap;
    ImageButton imgBtn_baitap_back;

    private UserDataSource datasource;

    SharedPreferences sharedPreferences;
    //SharedPreferences.Editor editor;
    String getType ;
    String email = null;
    float tongcalo;
    //GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
    Calendar currentDate = Calendar.getInstance();
    int stepCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tonghopbaitap);
        getSupportActionBar().hide();
        datasource = new UserDataSource(this);
        datasource.open();
        addControls();
        addEvents();
        getType=DateFormat.getDateInstance(DateFormat.FULL).format(currentDate.getTime());
        //
        list.clear();
        list = datasource.getAllExcersise();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        lv_Baitap.setAdapter(arrayAdapter);
        //
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
    List<Float> myList = new ArrayList<Float>();
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == msensor.TYPE_STEP_COUNTER){
            int checkDate = currentDate.get(Calendar.HOUR_OF_DAY);
            if(checkDate == 0){
                myList.clear();
                stepCount = 0;
                stepcounter.setText(String.valueOf(stepCount));
                DecimalFormat df = new DecimalFormat("#.##");
                double stepCalo = 0.3 * stepCount;
                caloStep.setText(df.format(stepCalo));
            }else {
                stepCount = (int) event.values[0];
                stepcounter.setText(String.valueOf(stepCount));
                DecimalFormat df = new DecimalFormat("#.##");
                double stepCalo = 0.3 * stepCount;
                caloStep.setText(df.format(stepCalo));
                Context context = getApplicationContext();
                sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                tongcalo = sharedPreferences.getFloat("tongcalo", -1);
                myList.add(tongcalo);
                float tongCaloCaiBaitap =0;
                for(int i = 0;i<myList.size();i++){
                    float currentValue = myList.get(i);
                    tongCaloCaiBaitap += currentValue;
                }
                float tongAll = (float) (tongCaloCaiBaitap+stepCalo);
                totalCalo.setText(df.format(tongAll));
                sharedPreferences = context.getSharedPreferences(getType, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putFloat(getType, (float) tongAll);
                editor.apply();
            }
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
//        Context context = getApplicationContext();
//        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
//        tongcalo = sharedPreferences.getFloat("tongcalo",-1);
//        totalCalo.setText(String.valueOf(tongcalo));
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
        lv_Baitap = (ListView) findViewById(R.id.lv_Baitap);
        totalCalo = (TextView) findViewById(R.id.totalCalo);

    }

    private void addEvents(){
        imgBtn_baitap_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_Baitap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPreferences = getSharedPreferences(ttluu,MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPreferences.edit();
                position += 1;
                int checkID = sharedPreferences.getInt("idExce",position);

                TapLuyen tapLuyen = datasource.detail_Excersise(checkID);
                if(tapLuyen == null){
                    Toast.makeText(getApplicationContext(),"Lỗi gì đó",Toast.LENGTH_LONG).show();
                }else {
                    String nameFoob = tapLuyen.getExerciseMenu_name();
                    Double calo  = tapLuyen.getExcercise_calo();
                    Intent intent = new Intent(getApplicationContext(), chitietbaitap.class);
                    intent.putExtra("name",nameFoob);
                    intent.putExtra("calo",calo);
                    startActivity(intent);
                }
                editor.putInt("idFood", 0);
                editor.commit();
            }

        });
    }


}