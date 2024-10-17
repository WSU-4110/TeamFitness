package com.example.myapplication;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpPage extends AppCompatActivity {

    // Initialize Button to go back to log in
    Button backToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        backToLogIn = findViewById(R.id.loginButton);

        backToLogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // If the user clicks on the sign up button
                // it redirects them back to the main log in page
                Intent intent = new Intent(SignUpPage.this, MainActivity.class );
                startActivity(intent);
                finish();
            }
        });
    }

}