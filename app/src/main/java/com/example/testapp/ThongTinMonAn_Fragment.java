package com.example.testapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testapp.DAO.UserDataSource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongTinMonAn_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongTinMonAn_Fragment extends Fragment {
    View view;
    private UserDataSource datasource;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongTinMonAn_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongTinMonAn_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongTinMonAn_Fragment newInstance(String param1, String param2) {
        ThongTinMonAn_Fragment fragment = new ThongTinMonAn_Fragment();
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
        view = inflater.inflate(R.layout.fragment_menu_food_,container,false);

        datasource = new UserDataSource(getContext());
        datasource.open();
        TextView tv_nameFood = (TextView)view.findViewById(R.id.tv_nameFood);
        TextView tv_chatProtein = (TextView)view.findViewById(R.id.tv_chatProtein);
        TextView tv_chatbeo = (TextView)view.findViewById(R.id.tv_chatbeo);
        ImageButton imagebtn_back2 =(ImageButton)view.findViewById(R.id.imagebtn_back2);
        TextView tv_carbs = (TextView)view.findViewById(R.id.tv_carbs);

        return view;
    }
}