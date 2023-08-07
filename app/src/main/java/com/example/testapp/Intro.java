package com.example.testapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

public class Intro extends AppIntro {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.createInstance(
                "Xin chào! 😄",
                "Calo App sẽ giúp bạn quản lý calories phù hợp với mục tiêu cân nặng",
                R.drawable.hoaman,
                R.color.xanhGood
        ));


        addSlide(AppIntroFragment.createInstance(
                "Meats",
                "Quản lý mọi bữa ăn của bạn theo ngày ",
                R.drawable.saly42,
                R.color.carrot
        ));
        addSlide(AppIntroFragment.createInstance(
                "Chart",
                "Các biểu đồ về giúp bạn theo dõi cân nặng và lượng calo cho nạp vào cho 1 ngày",
                R.drawable.linechart,
                R.color.purple_500
        ));


        addSlide(AppIntroFragment.createInstance(
                "Một xíu nữa thôi...",
                "App giúp bạn quản lý đơn giản hơn nhưng " +
                        "chính bạn là yếu tố then chốt mang lại thành quả.\n \n Nhóm xin chúc bạn có được 1 cơ thể  \nkhỏe mạnh. 🥰",
                R.drawable.saly37,
                R.color.ame
        ));


        // Fade Transition
        setTransformer(new AppIntroPageTransformerType.Parallax());
        // Show/hide status bar
        showStatusBar(true);
        //Enable the color "fade" animation between two slides (make sure the slide implements SlideBackgroundColorHolder)
        setColorTransitionsEnabled(true);

        //Prevent the back button from exiting the slides
        setSystemBackButtonLocked(true);

        //Activate wizard mode (Some aesthetic changes)
        setWizardMode(true);


        //Enable/disable page indicators
        setIndicatorEnabled(true);

        //Dhow/hide ALL buttons
        setButtonsEnabled(true);

        this.isColorTransitionsEnabled();

    }
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
