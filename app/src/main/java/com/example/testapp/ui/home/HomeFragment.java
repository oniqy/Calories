package com.example.testapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapp.DAO.UserDataSource;
import com.example.testapp.ImageLoadTask;
import com.example.testapp.MenuFood_Fragment;
import com.example.testapp.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp.TimMonAn;
import com.example.testapp.databinding.FragmentHomeBinding;
import com.example.testapp.ui.notifications.BMR_page_Fragment;
import com.example.testapp.ui.notifications.Input_Info_Fragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;
public class HomeFragment extends Fragment {
    private UserDataSource datasource;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView userImg;
    TextView userHello,chiSoCalo,userDate;
    BMR_page_Fragment bmr_page_fragment;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        datasource = new UserDataSource(getContext());
        datasource.open();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getContext(),gso);
        userImg = binding.userImg.findViewById(R.id.userImg);
        userHello = binding.userHello.findViewById(R.id.userHello);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct!=null){
            String personName = acct.getDisplayName();
            String Uri = acct.getPhotoUrl().toString();
            userHello.setText("Hi,"+personName);
            new ImageLoadTask(Uri,userImg).execute();
        }
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        userDate = binding.userDate.findViewById(R.id.userDate);
        userDate.setText(currentDate);
        binding.btnVEdtDailyFoob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  i = datasource.createFood();
                if(i == -1){

                }else {
               loadFragment(new MenuFood_Fragment());}
            }
        });
        binding.textNotificationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Input_Info_Fragment());
            }
        });

//
        return root;
    }

    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.fmg_homme, fragment);
        ft.commit(); // save the changes
    }
}