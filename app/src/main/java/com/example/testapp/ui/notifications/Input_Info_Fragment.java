package com.example.testapp.ui.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testapp.Custom_spinner;
import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.UserInfo;
import com.example.testapp.DangKyPage;
import com.example.testapp.R;
import com.example.testapp.adapter_gioitinh;
import com.example.testapp.adapter_mucTieu;
import com.example.testapp.adapter_tapluyen;
import com.example.testapp.databinding.FragmentInputInfoBinding;
import com.example.testapp.item_gioitinh;
import com.example.testapp.item_mucTieu;
import com.example.testapp.item_tapluyen;
import com.example.testapp.ui.home.HomeFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Input_Info_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Input_Info_Fragment extends Fragment implements Custom_spinner.OnSpinnerEventsListener  {
    View view;
    ArrayList<item_gioitinh> item_gioitinhs;
    ArrayList<item_tapluyen> item_tapluyens;
    ArrayList<item_mucTieu> item_mucTieus;
    adapter_gioitinh adapterGioitinh;
    adapter_mucTieu adapterMucTieu;
    adapter_tapluyen adapterTapluyen;
    Custom_spinner spinner_sex,spinner_tapLuyen,spinner_muctieu;
    Button btn_luuBMi;
    EditText edt_age,edt_height,edt_weight,edt_ten;
    ImageButton imagebtn_back;
    private UserDataSource datasource;
    private  BMR_page_Fragment bmr_page_fragment;

    private FragmentInputInfoBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;

    public Input_Info_Fragment() {
        // Required empty public constructor
    }
    String getSex,getCheDoTap,getMucTieu;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Input_Info_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Input_Info_Fragment newInstance(String param1, String param2) {
        Input_Info_Fragment fragment = new Input_Info_Fragment();
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
    private void addControl(){

    }
    private void addEvent(){
        btn_luuBMi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadFragment(new HomeFragment());
                ttUser();
            }
        });
    }
    private void ttUser(){
        UserInfo userInfo = new UserInfo();
        String name = edt_ten.getText().toString();
        int age = Integer.parseInt(edt_age.getText().toString());
        int height = Integer.parseInt(edt_height.getText().toString());
        int weight = Integer.parseInt(edt_weight.getText().toString());
        userInfo.setName(name);
        userInfo.setBirthDay(age);
        userInfo.setUserHeight(height);
        userInfo.setUserWeight(weight);
        userInfo.setGender(getSex);
        userInfo.setExercise(getCheDoTap);
        userInfo.setTarget(getMucTieu);
        int t = datasource.createUserInfo(userInfo);
        if(t == 1){
            Toast.makeText(getContext(), "Lưu thông tin thành công", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), "Có gì đó sai sai", Toast.LENGTH_LONG).show();

        }


    }
    private void gioitinh(){
        int[] img =new int[]{R.drawable.lavatory,R.drawable.lavatory1};
        String[] sex = new String[]{"Nam","Nữ"};

        spinner_sex.setSpinnerEventsListener(this);
        item_gioitinhs= item_gioitinh.initSex(img,sex);
        adapterGioitinh =new adapter_gioitinh(this.getLayoutInflater(),item_gioitinhs,R.layout.item_ender);
        spinner_sex.setAdapter(adapterGioitinh);
        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSex = item_gioitinhs.get(position).getSex().toString();
                Toast.makeText(getContext(),item_gioitinhs.get(position).getSex(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void chedotap(){
        String[] chedo = new String[]{"Không tập","Nhẹ nhàng","Vừa phải","Nặng"};
        String[] mota = new String[]{"Ít hoạt động chỉ đi làm rồi về ngủ","Tập nhẹ nhàng 1-3 buổi/tuần","Vận động vừa 3-5 buổi/tuần"," Vận động nhiều 5-7 buổi/tuần"};


        item_tapluyens = item_tapluyen.inittapluyen(chedo,mota);
        adapterTapluyen = new adapter_tapluyen(this.getLayoutInflater(),item_tapluyens,R.layout.item_tapluyen);
        spinner_tapLuyen.setAdapter(adapterTapluyen);
        spinner_tapLuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCheDoTap = item_tapluyens.get(position).getChedo().toString();
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

        item_mucTieus = item_mucTieu.initMuctieu(chedo,mota);
        adapterMucTieu = new adapter_mucTieu(this.getLayoutInflater(),item_mucTieus,R.layout.item_tapluyen);
        spinner_muctieu.setAdapter(adapterMucTieu);
        spinner_muctieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getMucTieu = item_mucTieus.get(position).getChedo().toString();
                Toast.makeText(getContext(),item_mucTieus.get(position).getChedo(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInputInfoBinding.inflate(inflater, container, false);
        datasource = new UserDataSource(getContext());
        datasource.open();
        binding.imagebtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new BMR_page_Fragment());
            }
        });
        btn_luuBMi = (Button) binding.btnLuuBMi.findViewById(R.id.btn_luuBMi);
        edt_age = (EditText) binding.edtAge.findViewById(R.id.edt_age);
        edt_ten = (EditText) binding.edtTen.findViewById(R.id.edt_ten);
        edt_height = (EditText) binding.edtHeight.findViewById(R.id.edt_height);
        spinner_sex = (Custom_spinner)binding.spinnerSex.findViewById(R.id.spinner_sex);
        edt_weight = (EditText) binding.edtWeight.findViewById(R.id.edt_weight);
        spinner_tapLuyen =(Custom_spinner)binding.spinnerTapLuyen.findViewById(R.id.spinner_tapLuyen);
        spinner_muctieu =(Custom_spinner) binding.spinnerMuctieu.findViewById(R.id.spinner_muctieu);
        imagebtn_back = (ImageButton) binding.imagebtnBack.findViewById(R.id.imagebtn_back);
        gioitinh();
        chedotap();
        mucjtieu();
        addEvent();
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
        ft.replace(R.id.Info_fragment, fragment);
        ft.commit(); // save the changes
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {

    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {

    }
}