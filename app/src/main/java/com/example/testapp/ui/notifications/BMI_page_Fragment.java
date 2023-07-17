package com.example.testapp.ui.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testapp.R;
import com.example.testapp.databinding.FragmentBMIPageBinding;
import com.example.testapp.databinding.FragmentNotificationsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BMI_page_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class BMI_page_Fragment extends Fragment {
    View view;
    ImageButton imagebtn_back2;
    EditText edt_BMI_cc,edt_BMI_cn;
    Button btn_luu;
    TextView tv_kqBMI,tetV_kq1,tv_nx;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public BMI_page_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BMI_page_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BMI_page_Fragment newInstance(String param1, String param2) {
        BMI_page_Fragment fragment = new BMI_page_Fragment();
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
        view = inflater.inflate(R.layout.fragment_b_m_i_page_,container,false);
        tv_kqBMI =(TextView) view.findViewById(R.id.tv_kqBMI);
        tetV_kq1 =(TextView) view.findViewById(R.id.tetV_kq1);
        tv_nx =(TextView) view.findViewById(R.id.tv_nx);
        imagebtn_back2 = (ImageButton) view.findViewById(R.id.imagebtn_back2);
        btn_luu = (Button) view.findViewById(R.id.btn_luu);
        edt_BMI_cc = (EditText) view.findViewById(R.id.edt_BMI_cc);
        edt_BMI_cn = (EditText) view.findViewById(R.id.edt_BMI_cn);
        imagebtn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new NotificationsFragment());
            }
        });
       btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float Cm = Float.parseFloat(edt_BMI_cc.getText().toString());
                float M = (float) (Cm * 0.01);
                float cn = Float.parseFloat(edt_BMI_cn.getText().toString());
                float BMI = cn / (M*M);

                tv_kqBMI.setText(Float.toString(Math.round(BMI * 10) / 10));
                tetV_kq1.setText(Float.toString(Math.round(BMI * 10) / 10));
                if(BMI < 16){
                    tv_nx.setText("Chỉ số BMI ở trên cho thấy bạn đang Gầy độ III");
                }
                else if(BMI >= 16 && BMI < 17){
                    tv_nx.setText("Chỉ số BMI ở trên cho thấy bạn đang Gầy độ II");
                }
                else if(BMI >= 17  && BMI < 18.5){
                    tv_nx.setText("Chỉ số BMI ở trên cho thấy bạn đang Gầy độ I");
                }
                else if(BMI >= 18.5  && BMI < 25){
                    tv_nx.setText("Chỉ số BMI ở trên cho thấy bạn có 1 cơ thể Bình thường");
                }
                else if(BMI >= 25   && BMI < 30){
                    tv_nx.setText("Chỉ số BMI ở trên cho thấy bạn đang Thừa cân");
                }
                else if(BMI >= 30   && BMI < 35){
                    tv_nx.setText("Chỉ số BMI ở trên cho thấy bạn đang Béo phì độ I");
                }
                else if(BMI >= 35   && BMI < 40){
                    tv_nx.setText("Chỉ số BMI ở trên cho thấy bạn đang Béo phì độ II");
                }
                else {
                    tv_nx.setText("Chỉ số BMI ở trên cho thấy bạn đang Béo phì độ III");
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
        ft.replace(R.id.fragment_BMI, fragment);
        ft.commit(); // save the changes
    }
}