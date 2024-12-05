package com.example.myapplication;

import android.annotation.SuppressLint;
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
    private EditText newPasswordEdit;

    private Button resetPasswordButton;

    private FirebaseAuth mAuth;

    public boolean passwordCheck(String password) {
        if(null == password){
            return false;
        }
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.email);
        newPasswordEdit = findViewById(R.id.password);
        resetPasswordButton = findViewById(R.id.resetPassword);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String newPassword = newPasswordEdit.getText().toString();

                if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }
                if (passwordCheck(newPassword)) {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassword.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Error! Reset link not sent: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    Toast.makeText(ForgotPassword.this, "Confirm Password in Email"
                            , Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ForgotPassword.this, "Must have a min. length of 8, a special character, a capital letter, and a number."
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
