package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.DTO.FoodMenu;
import com.example.testapp.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFood_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFood_Fragment extends Fragment {
    View view;
    private UserDataSource datasource;
    ArrayAdapter<String> arrayAdapter;
    List<String> list = new ArrayList<>();
    ListView lsV;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFood_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFood_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFood_Fragment newInstance(String param1, String param2) {
        MenuFood_Fragment fragment = new MenuFood_Fragment();
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
        view = inflater.inflate(R.layout.fragment_menu_food_,container,false);

        datasource = new UserDataSource(getContext());
        datasource.open();

        lsV = (ListView) view.findViewById(R.id.lsV);
        ImageButton btnV_search = (ImageButton) view.findViewById(R.id.btnV_search);
        ImageButton imagebtn_back2 = (ImageButton)view.findViewById(R.id.imagebtn_back2);
        EditText edtTimKiemFoob= (EditText)view.findViewById(R.id.edtTimKiemFoob);
        list.clear();
        list = datasource.getAllfood();
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,list);
        lsV.setAdapter(arrayAdapter);

        btnV_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFood = edtTimKiemFoob.getText().toString();
                list.clear();
                list = datasource.timKiemfood(nameFood);
                arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,list);
                lsV.setAdapter(arrayAdapter);


            }
        });
        imagebtn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });
        lsV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodMenu foodMenu = datasource.detail_food(position);
                if(foodMenu == null){
                    Toast.makeText(getContext(),"Lỗi gì đó",Toast.LENGTH_LONG).show();
                }else {
                    String nameFoob = foodMenu.getFoodName();
                    String Proteins = String.valueOf(foodMenu.getProteins());
                    String Carbs = String.valueOf(foodMenu.getCarbs());
                    String fats = String.valueOf(foodMenu.getFats());
                    Intent intent = new Intent(getContext(), ThongTinMonAn.class);
                    intent.putExtra("name",nameFoob);
                    intent.putExtra("Proteins",Proteins);
                    intent.putExtra("Carbs",Carbs);
                    intent.putExtra("fats",fats);
                    startActivity(intent);
                }
            }

        });
        return view;
    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.fmg_searchFood, fragment);
        ft.commit(); // save the changes
    }
}