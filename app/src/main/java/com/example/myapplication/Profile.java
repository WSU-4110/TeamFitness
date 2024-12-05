package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private Button settingsButton, accessibilityButton, notificationsButton, bioButton, returnHomeButton, logOutButton;
    private TextView nameTextView, emailTextView;

    private FirebaseAuth fAuth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Initialize Firebase
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        // Initialize UI components
        accessibilityButton = findViewById(R.id.button2);
//ginpre
        bioButton = findViewById(R.id.button4);
        returnHomeButton = findViewById(R.id.HomeButton);
        logOutButton = findViewById(R.id.logOut);
        nameTextView = findViewById(R.id.textView); // TextView for the name
        emailTextView = findViewById(R.id.textView2); // TextView for the email

        // Load user details from Firebase
        loadUserDetails();

        // Set up button click listeners for navigation
//        settingsButton.setOnClickListener(v -> {
//            Intent intent = new Intent(Profile.this, SettingsActivity.class);
//            startActivity(intent);
//        });

        accessibilityButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, AccessibilityActivity.class);
            startActivity(intent);
        });

//        notificationsButton.setOnClickListener(v -> {
//            Intent intent = new Intent(Profile.this, NotificationsActivity.class);
//            startActivity(intent);
//        });

        bioButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, BioActivity.class);
            startActivityForResult(intent, 1); // Start BioActivity and wait for a result
        });

        returnHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        logOutButton.setOnClickListener(v -> {
            fAuth.signOut(); // Sign out the user
            Intent intent = new Intent(Profile.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Load the user details from Firebase and display them in the TextViews
    private void loadUserDetails() {
        FirebaseUser currentUser = fAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = db.child("users").child(userId).child("userName");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Extract username and email from the database
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String username = (String) snapshot.child("Username").getValue();
                            if (username != null) {
                                nameTextView.setText(username);
                            }
                        }
                    }

                    // Also get the user's email directly from FirebaseAuth
                    String email = currentUser.getEmail();
                    if (email != null) {
                        emailTextView.setText(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Log or handle database error
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the user details when returning to this activity
        loadUserDetails();
    }
}
