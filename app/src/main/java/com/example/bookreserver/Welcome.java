package com.example.bookreserver;

/*
*   created on 16/07/2019
*   by pratik katriya
*   CSE student
* */

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.student_act.bookreserver.Home;
import com.student_act.bookreserver.Student;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    Button student;
    Button librarian;

    ImageView book1;
    ImageView book2;
    ImageView book3;
    ImageView book4;

    View view;
    TextView textView;
    TextView textView2;


    public void initializeFields() {
        student = findViewById(R.id.student_text);
        librarian = findViewById(R.id.librarian_text);

        book1 = findViewById(R.id.book1);
        book2 = findViewById(R.id.book2);
        book3 = findViewById(R.id.book3);
        book4 = findViewById(R.id.book4);

        textView = findViewById(R.id.CDGI_Lib);
        textView2 = findViewById(R.id.CDGI_Lib_advance);

        view  = findViewById(R.id.bottomRack);

        student.setOnClickListener(this);
        librarian.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(Welcome.this, Student.class));

      FirebaseAuth firebaseAuth  = FirebaseAuth.getInstance();
      FirebaseUser firebaseUser  = firebaseAuth.getCurrentUser();


        if (firebaseUser != null)
            startActivity(new Intent(Welcome.this, Student.class));

        initializeFields();

//        View animation = findViewById(R.id.view);
//        final Handler handler = new Handler();
//        handler.postDelayed(() -> {
//            //Do something after 100ms
//            animation.setVisibility(View.VISIBLE);
//            Animation ani = AnimationUtils.loadAnimation(this,  R.anim.fadein);
//            animation.startAnimation(ani);
//        }

        final Handler handler = new Handler();

        book1.setVisibility(View.VISIBLE);
        animateOnScreen(book1, 142);

        handler.postDelayed(() -> {
            //Do something after 100ms
            book2.setVisibility(View.VISIBLE);
            animateOnScreen(book2, 142);
        }, 400);

        handler.postDelayed(() -> {
            //Do something after 100ms
            book3.setVisibility(View.VISIBLE);
            animateOnScreen(book3, 142);
        }, 600);


        handler.postDelayed(() -> {
            //Do something after 100ms

            book4.setVisibility(View.VISIBLE);
            animateOnScreen(book4, 142);
        }, 800);

        handler.postDelayed(() -> {
            textView.setVisibility(View.VISIBLE);
            animateOnScreen(textView, -38);
            textView2.setVisibility(View.VISIBLE);
            animateOnScreen(textView2, -50);
            view.setVisibility(View.VISIBLE);
            animateOnScreen(view, -30);
        }, 1000);

        handler.postDelayed(() -> {
            ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(book4,
                    "rotation", 0f, 45f);
            imageViewObjectAnimator.setDuration(1000); // miliseconds
            imageViewObjectAnimator.start();
        }, 1500);
        animateOnScreen(book4, 64);


        handler.postDelayed(() -> {
            animateOnScreenToX(book1,-150);
            animateOnScreenToX(book2,-150);
            animateOnScreenToX(book3,-150);
        },2000);

        handler.postDelayed(() -> {

            Animation animation = new TranslateAnimation(0, 0, 0, 60
            );
            animation.setDuration(1000);
            animation.setFillAfter(true);
            book1.startAnimation(animation);
            book2.startAnimation(animation);
        }, 3000);

//        handler.postDelayed(() -> {
//            view.setVisibility(View.VISIBLE);
//            animateOnScreen(view, -20);
//        }, 3000);
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.student_text:
                Toast.makeText(this, "student", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Welcome.this, StudentLogin.class));
                break;
            case R.id.librarian_text:
                Toast.makeText(this, "librarian", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Welcome.this, Librarian.class));
                break;
        }
    }

    private void animateOnScreen(View view, float value ) {
        ObjectAnimator animationobj = ObjectAnimator.ofFloat(view, "translationY", value);
        animationobj.setDuration(1000);
        animationobj.start();
    }

    private void animateOnScreenToX(View view, float value ) {
        ObjectAnimator animationobj = ObjectAnimator.ofFloat(view, "translationX", value);
        animationobj.setDuration(1000);
        animationobj.start();
    }
}
