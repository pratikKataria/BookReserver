package com.example.bookreserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Librarian extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email_edit_text;
    EditText pass_edit_text;
    TextView loginSuccessAnim;
    private ProgressBar mProgressBar;

    Button continue_btn;

    public void initilizeFields() {
        email_edit_text = findViewById(R.id.email_edit_text);
        pass_edit_text  = findViewById(R.id.pass_edit_text);
        mProgressBar    = findViewById(R.id.progressBar);
        continue_btn    = findViewById(R.id.continue_btn);
        loginSuccessAnim= findViewById(R.id.loginSuccessAnim);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian);
        initilizeFields();
        changeStatusBarColor();

            continue_btn.setOnClickListener(v -> {
            String email = email_edit_text.getText().toString();
            String pass  = pass_edit_text.getText().toString();

            if (email.isEmpty()) {
                email_edit_text.setError("email required");
                email_edit_text.requestFocus();
                return;
            }

            if (pass.isEmpty()) {
                pass_edit_text.setError("field should not be empty");
                pass_edit_text.requestFocus();
                return;
            }
            signIn();
        });

    }

    private void signIn() {
        String email = email_edit_text.getText().toString();
        String pass  = pass_edit_text.getText().toString();
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            /**design code with animation */
                            mProgressBar.setVisibility(View.GONE);
                            if (loginSuccessAnim.getVisibility() == View.INVISIBLE) {
                                loginSuccessAnim.setVisibility(View.VISIBLE);
                                animateOnScreen(loginSuccessAnim);
                                animateOnScreen(continue_btn);
                            }

                            //Handler delay
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                //Do something after 100ms
//                                startActivity(new Intent(this, ScanQR.class));
                            }, 1500);

                            //animation ends

                        }
                    });
    }

    private void animateOnScreen(View view) {
        ObjectAnimator animationobj = ObjectAnimator.ofFloat(view, "translationY", 50f);
        animationobj.setDuration(1000);
        animationobj.start();
    }


    public void changeStatusBarColor() {
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purple));
    }
}
