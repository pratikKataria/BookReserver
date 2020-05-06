package com.example.bookreserver;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.student_act.bookreserver.Student;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signup extends Fragment {


    public Signup() {
        // Required empty public constructor
    }

    private EditText mEmail;
    private EditText mEnrollNo;
    private EditText mPass;
    private EditText mConfPass;
    private Button mSignupBtn;
    private TextView msignupSuccess;
    private TextView mFailer;

    private ProgressBar progressBar;

    FirebaseAuth mAtuh;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mAtuh = FirebaseAuth.getInstance();

        mEmail          = view.findViewById(R.id.email_edit_text);
        mEnrollNo       = view.findViewById(R.id.enrollment_number);
        mPass           = view.findViewById(R.id.pass_edit_text);
        mConfPass       = view.findViewById(R.id.con_pass_edit_text);
        mSignupBtn      = view.findViewById(R.id.signup_btn);
        msignupSuccess  = view.findViewById(R.id.signupSuccessAnim);
        progressBar     = view.findViewById(R.id.progressBar);
        mFailer         = view.findViewById(R.id.failedsignup);
        databaseReference = FirebaseDatabase.getInstance().getReference("user credentials");

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pass  = mPass.getText().toString();
                String conPass = mConfPass.getText().toString();
                String enrollmentNumb = mEnrollNo.getText().toString();

                if (email.isEmpty()) {
                    mEmail.setError("Email is require");
                    mEmail.requestFocus();
                    return ;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmail.setError("please enter a valid email");
                    mEmail.requestFocus();
                    return ;
                }

                if (pass.isEmpty()) {
                    mPass.setError("pass is requied");
                    mPass.requestFocus();
                    return ;
                }

                if (pass.length() < 6) {
                    mPass.setError("Minimum length of password should be 6");
                    mPass.requestFocus();
                    return ;
                }

                if (conPass.isEmpty()) {
                    mConfPass.setError("please enter conPass");
                    mConfPass.requestFocus();
                    return;
                }

                if (enrollmentNumb.isEmpty()) {
                    mEnrollNo.setError("enrollment number not be empty");
                    mEnrollNo.requestFocus();
                    return;
                }

                if (pass.equals(conPass)) {
                    progressBar.setVisibility(View.VISIBLE);
                    mAtuh.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(task -> {
                                progressBar.setVisibility(View.GONE);
                               if (task.isSuccessful()) {
                                   msignupSuccess.setVisibility(View.VISIBLE);
                                   animateOnScreen(msignupSuccess, 50);
                                   animateOnScreen(mSignupBtn, 50);

                                   StudentDataModel studentDataModel = new StudentDataModel(email, enrollmentNumb, pass);
//                                   String postId = databaseReference.push().getKey();
                                   databaseReference.child(enrollmentNumb).setValue(studentDataModel);

                                   Handler handler = new Handler();
                                   handler.postDelayed(() -> {
                                       startActivity(new Intent(getActivity(), Student.class));
                                   }, 1500);

                               } else {
                                   if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                       mFailer.setVisibility(View.VISIBLE);
                                       animateOnScreen(mFailer, 50);
                                       animateOnScreen(mSignupBtn, 50);

                                       Handler handler1 = new Handler();
                                       handler1.postDelayed(() -> {
                                           animateOnScreen(mFailer,-50);
                                           animateOnScreen(mSignupBtn,-25);
                                           mFailer.setVisibility(View.INVISIBLE);
                                       }, 1000);
                                   }
                               }
                            });

                }

            }
        });



        return view;
    }

    private void animateOnScreen(View view, float pos) {
        ObjectAnimator animationobj = ObjectAnimator.ofFloat(view, "translationY", pos);
        animationobj.setDuration(1000);
        animationobj.start();
    }

}
