package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;



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
    EditText username;

    private FirebaseAuth fAuth;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public boolean firstNameCheck(String firstName){
        if(null == firstName){
            return false;
        }
        else{
            String firstNameRegex = "^[A-Za-z]+(?: [A-Za-z-]+)*$";
            Pattern pattern = Pattern.compile(firstNameRegex);
            Matcher matcher = pattern.matcher(firstName);
            return matcher.matches();
        }
    }

    public boolean lastNameCheck(String lastName){
        if(null == lastName){
            return false;
        }
        else{
            String lastNameRegex = "^[A-Za-z]+(?: [A-Za-z-]+)*$";
            Pattern pattern = Pattern.compile(lastNameRegex);
            Matcher matcher = pattern.matcher(lastName);
            return matcher.matches();
        }
    }

    public boolean phoneNumberCheck(String phoneNumber){
        if(null == phoneNumber || phoneNumber.isEmpty()){
            return false;
        }
        else{
            String phoneRegex = "^(\\+?[0-9]{1,3})?[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$";
            Pattern pattern = Pattern.compile(phoneRegex);
            Matcher matcher = pattern.matcher(phoneNumber);
            return matcher.matches();
        }
    }

    public boolean emailCheck(String email) {
        if(null == email){
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean passwordCheck(String password) {
        if(null == password){
            return false;
        }
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean passwordSame(String password1, String password2) {
        if (Objects.equals(password1, password2)) {
            return true;
        }
        else{
            return false;
        }
    }

    private void firebaseSignUp(String userEmail, String userPassword, String strUserName){

        fAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Registration in was successful

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String tableId = db.child("users").getKey();

                    Map<String, Object> tableData = new HashMap<>();

                    tableData.put("Username",strUserName);

                    db.child("users").child(userId).child("userName").child(tableId)
                            .setValue(tableData)
                            .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                            .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));

                    Toast.makeText(SignUpPage.this, "Registration in successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpPage.this, EmailVerificationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        // Email already in the database
                        Toast.makeText(SignUpPage.this, "Email already in use", Toast.LENGTH_SHORT).show();
                    } else {
                        // Registration failed for some other reason
                        Toast.makeText(SignUpPage.this, "Registration in failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        backToLogIn = findViewById(R.id.loginButton);
        registered = findViewById(R.id.registerButton);

        backToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user clicks on the sign up button
                // it redirects them back to the main log in page
                Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);


        fAuth = FirebaseAuth.getInstance();

        registered.setOnClickListener(new View.OnClickListener() {
          
            @Override
            public void onClick(View v) {
                // Getting the user inputs
                String strUserName = username.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userConfirmPassword = confirmPassword.getText().toString();

                // Checks the users inputs
                if (emailCheck(userEmail) && passwordCheck(userPassword)
                        && passwordSame(userPassword, userConfirmPassword))
                {

                    firebaseSignUp(userEmail, userPassword, strUserName);

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
            }
        });
    }
}

