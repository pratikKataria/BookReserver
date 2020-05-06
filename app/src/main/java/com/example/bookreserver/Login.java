package com.example.bookreserver;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.DragStartHelper;
import androidx.fragment.app.Fragment;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.student_act.bookreserver.Student;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    FirebaseAuth mAuth;
    private TextView loginSuccessAnim;
    private ProgressBar progressBar;
    private Button continue_btn;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) startActivity(new Intent(getActivity(), Student.class));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null ) startActivity(new Intent(getActivity(), Student.class));
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        EditText email = view.findViewById(R.id.email_edit_text);
        EditText pass = view.findViewById(R.id.password_edit_text);
        progressBar = view.findViewById(R.id.progressBar);
        loginSuccessAnim = view.findViewById(R.id.loginSuccessAnim);

        continue_btn = view.findViewById(R.id.continue_btn);
        Toast.makeText(getActivity(), "hi this login frag", Toast.LENGTH_SHORT).show();


        continue_btn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Student.class));
            String mEmail = email.getText().toString();
            String mPass = pass.getText().toString();

            if (mEmail.isEmpty()) {
                email.setError("field should not be empty");
                email.requestFocus();
                return;
            }

            if (mPass.isEmpty()) {
                pass.setError("password should not be null");
                pass.requestFocus();
                return;
            }

            doSignIn(mEmail, mPass);
                Toast.makeText(getActivity(), "hi this login frag", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void animateOnScreen(View view) {
        ObjectAnimator animationobj = ObjectAnimator.ofFloat(view, "translationY", 50f);
        animationobj.setDuration(1000);
        animationobj.start();
    }

    private void doSignIn(String email, String pass) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        loginSuccessAnim.setVisibility(View.VISIBLE);
                        animateOnScreen(loginSuccessAnim);
                        animateOnScreen(continue_btn);
                    }

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(() -> {
                        //Do something after 100ms
//                                startActivity(new Intent(this, ScanQR.class));
                        startActivity(new Intent(getActivity(), Student.class));
                    }, 1500);
                });
    }

}
