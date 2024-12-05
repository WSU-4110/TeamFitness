package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ForgotPassword extends AppCompatActivity {
    private EditText emailEditText;

    private Button resetPasswordButton;
    private Button backToLogButton;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

            mAuth = FirebaseAuth.getInstance();

            emailEditText = findViewById(R.id.email);
            resetPasswordButton = findViewById(R.id.resetPassword);

            resetPasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailEditText.getText().toString().trim();

                    if (email.isEmpty()) {
                        emailEditText.setError("Email is required");
                        emailEditText.requestFocus();
                        return;
                    }

                    // Send password reset email
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassword.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Error! Reset link not sent: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                    Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        backToLogButton = findViewById(R.id.backToLogIn);


        backToLogButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
