package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class EmailVerificationActivity extends AppCompatActivity {

    Button loginButtonVerification;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_verification);

         FirebaseAuth mAuth;
         mAuth = FirebaseAuth.getInstance();
            final FirebaseUser user = mAuth.getCurrentUser();
            user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    // Re-enable button
                    findViewById(R.id.registerButton).setEnabled(true);

                    if (task.isSuccessful()) {
                        Toast.makeText(EmailVerificationActivity.this,
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EmailVerificationActivity.this,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            loginButtonVerification = findViewById(R.id.loginButtonVerification);

            loginButtonVerification.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(EmailVerificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
    }
}
