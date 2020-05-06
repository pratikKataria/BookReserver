package com.student_act.bookreserver;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookreserver.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Student extends AppCompatActivity implements BarCodeReader.BarCodeFragmentListner {

    private BottomNavigationView mMainNav;

    private Home mhome;
    private BookReservation mBookReservation;
    private ViewBookCode mViewBookCode;
    private Meeting mMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        FrameLayout mMainFrame = findViewById(R.id.frameLayout);
        mMainNav = findViewById(R.id.bottomNavigationView);

        mhome = new Home();
        mBookReservation = new BookReservation();
        mViewBookCode = new ViewBookCode();
        mMeeting = new Meeting();

        setFragment(mhome);

        mMainNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home_nav_btn:
                    setFragment(mhome);
                    return true;
                case R.id.get_book_nav_btn:
                    setFragment(mBookReservation);
                    return true;
                case R.id.view_book_nav_btn:
                    setFragment(mViewBookCode);
                    return true;
                case R.id.meeting:
                    setFragment(mMeeting);
                    return true;
                default:
                    return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onScanCode(String date, String scannedData) {
        mMeeting.updateStudentList(date, scannedData);
    }

}