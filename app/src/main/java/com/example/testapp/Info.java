package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.testapp.ui.home.HomeFragment;
import com.example.testapp.ui.notifications.NotificationsFragment;

import java.util.ArrayList;

public class Info extends AppCompatActivity implements Custom_spinner.OnSpinnerEventsListener {
    ArrayList<item_gioitinh> item_gioitinhs;
    ArrayList<item_tapluyen> item_tapluyens;
    adapter_gioitinh adapterGioitinh;
    adapter_tapluyen adapterTapluyen;
    Custom_spinner spinner_sex,spinner_tapLuyen,spinner_muctieu;
    Button btn_luuBMi;
    EditText edt_age,edt_height,edt_weight;
    ImageButton imagebtn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_info);
        addControl();
        addEvent();
        gioitinh();
        chedotap();
        mucjtieu();
    }
    private void addControl(){
        btn_luuBMi = (Button) findViewById(R.id.btn_luuBMi);
        edt_age = (EditText) findViewById(R.id.edt_age);
        edt_height = (EditText) findViewById(R.id.edt_height);
        edt_weight = (EditText) findViewById(R.id.edt_weight);
        imagebtn_back = (ImageButton) findViewById(R.id.imagebtn_back);
    }
    private void addEvent(){
        btn_luuBMi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Info.this, MainActivity.class);
                startActivity(intent);
            }
        });
        imagebtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSettingview = new Intent(Info.this, NotificationsFragment.class);
                startActivity(toSettingview);
            }
        });
    }
    private void gioitinh(){
        int[] img =new int[]{R.drawable.lavatory,R.drawable.lavatory1};
        String[] sex = new String[]{"Nam","Nữ"};
        spinner_sex = (Custom_spinner) findViewById(R.id.spinner_sex);
        spinner_sex.setSpinnerEventsListener(this);
        item_gioitinhs= item_gioitinh.initSex(img,sex);
        adapterGioitinh =new adapter_gioitinh(this.getLayoutInflater(),item_gioitinhs,R.layout.item_ender);
        spinner_sex.setAdapter(adapterGioitinh);
        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),item_gioitinhs.get(position).getSex(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void chedotap(){
        String[] chedo = new String[]{"Không tập","Nhẹ nhàng","Vừa phải","Nặng"};
        String[] mota = new String[]{"Ít hoạt động chỉ đi làm rồi về ngủ","Tập nhẹ nhàng 1-3 buổi/tuần","Vận động vừa 3-5 buổi/tuần"," Vận động nhiều 5-7 buổi/tuần"};
        spinner_tapLuyen =(Custom_spinner) findViewById(R.id.spinner_tapLuyen);

        item_tapluyens = item_tapluyen.inittapluyen(chedo,mota);
        adapterTapluyen = new adapter_tapluyen(this.getLayoutInflater(),item_tapluyens,R.layout.item_tapluyen);
        spinner_tapLuyen.setAdapter(adapterTapluyen);
        spinner_tapLuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),item_tapluyens.get(position).getChedo(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void mucjtieu(){
        String[] chedo = new String[]{"Giảm cân","Giữ nguyên cân nặng","Tăng cân"};
        String[] mota = new String[]{"Ăn uống thông minh hơn với Healthy care","Tối ưu cân nặng của bạn","Tăng cân với Healthy care"};
        spinner_muctieu =(Custom_spinner) findViewById(R.id.spinner_muctieu);
        item_tapluyens = item_tapluyen.inittapluyen(chedo,mota);
        adapterTapluyen = new adapter_tapluyen(this.getLayoutInflater(),item_tapluyens,R.layout.item_tapluyen);
        spinner_muctieu.setAdapter(adapterTapluyen);
        spinner_muctieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),item_tapluyens.get(position).getChedo(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        spinner_sex.setBackground(getResources().getDrawable(R.drawable.bg_spinner_gr_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        spinner_sex.setBackground(getResources().getDrawable(R.drawable.bg_spinner_gr));
    }
}