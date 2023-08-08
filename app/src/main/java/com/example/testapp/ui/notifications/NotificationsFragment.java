package com.example.testapp.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapp.ImageLoadTask;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp.DangNhapPage;
import com.example.testapp.R;
import com.example.testapp.databinding.FragmentNotificationsBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class NotificationsFragment extends Fragment {
    View view;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView profile_username,profile_userid;
    ImageView profile_image;
    ImageButton profile_logoutBtn;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getContext(),gso);
        profile_username = binding.profileUsername.findViewById(R.id.profile_username);
        profile_userid = binding.profileUserid.findViewById(R.id.profile_userid);
        profile_logoutBtn = binding.profileLogoutBtn.findViewById(R.id.profile_logoutBtn);
        profile_image = binding.profileImage.findViewById(R.id.profile_image);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            if (personPhoto == null){
                new ImageLoadTask("https://icons.veryicon.com/png/o/miscellaneous/two-color-icon-library/user-286.png",profile_image).execute();
            }
            else {
                profile_username.setText(personName);
                profile_userid.setText(personEmail);
                new ImageLoadTask(personPhoto.toString(), profile_image).execute();
            }
        }

        profile_logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });
        binding.buttonTtcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new QLtheoNam_fragment());
            }
        });
        binding.linearLayoutBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadFragment(new BMI_page_Fragment());

            }
        });
        binding.linearLayoutBmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new BMR_page_Fragment());

            }
        });
        binding.linearLayoutColories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ControlWeightFragment());
            }
        });
        View root = binding.getRoot();
        return root;
    }
    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                try {
                    finalize();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
                startActivity(new Intent(getContext(), DangNhapPage.class));
            }
        });
    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = requireActivity().getSupportFragmentManager();

        // Create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(androidx.transition.R.anim.fragment_open_exit, androidx.transition.R.anim.fragment_close_exit);
// replace the FrameLayout with new Fragment
        ft.replace(R.id.frag_1, fragment);
        ft.commit(); // save the changes
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}