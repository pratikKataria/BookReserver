package com.example.bookreserver;

import android.os.Build;
import android.os.Bundle;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;

import com.example.bookreserver.ui.main.SectionsPagerAdapter;

public class StudentLogin extends AppCompatActivity {

    private NavigationTabStrip mTopNavigationTabStrip;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mTopNavigationTabStrip = findViewById(R.id.nts_bottom);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mTopNavigationTabStrip.setViewPager(viewPager, 0);

        changeStatusBarColor();
    }

    public void changeStatusBarColor() {
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blueGreen));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flag = viewPager.getSystemUiVisibility();
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            viewPager.setSystemUiVisibility(flag);
        }
    }
}