package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

    // Initialize Button to go back to log in and to register
    Button backToLogIn;
    Button registered;

    /*
     Initializing the email, password, and confirmPassword to
     turn them to strings
    */
    EditText email;
    EditText password;
    EditText confirmPassword;

    public boolean emailCheck(String email){
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean passwordCheck(String password){
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean passwordSame(String password1, String password2){
        if(Objects.equals(password1, password2)){
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        backToLogIn = findViewById(R.id.loginButton);
        registered = findViewById(R.id.registerButton);

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

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        registered.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Getting the user inputs
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userConfirmPassword = confirmPassword.getText().toString();

                // Checks the users inputs
                if (emailCheck(userEmail) && passwordCheck(userPassword)
                        && passwordSame(userPassword, userConfirmPassword)) {
                        Intent intent = new Intent(SignUpPage.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                }
                // If the user enters an incorrect field it will let them know
                else {
                    if (!emailCheck(userEmail)) {
                        Toast.makeText(SignUpPage.this, "Invalid Email Format. EX: john@gmail.com",
                                Toast.LENGTH_SHORT).show();
                    } else if (!passwordCheck(userPassword)) {
                        Toast.makeText(SignUpPage.this, "Must have a min. length of 8, a special character, a capital letter, and a number."
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpPage.this, "Passwords must match.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            };
        });
    }