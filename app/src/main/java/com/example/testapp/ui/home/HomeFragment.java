package com.example.testapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testapp.MenuFood_Fragment;
import com.example.testapp.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp.TimMonAn;
import com.example.testapp.databinding.FragmentHomeBinding;
import com.example.testapp.ui.notifications.Input_Info_Fragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnVEdtDailyFoob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadFragment(new MenuFood_Fragment());
            }
        });
        binding.textNotificationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Input_Info_Fragment());
            }
        });

        final TextView textView = binding.userHello;
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