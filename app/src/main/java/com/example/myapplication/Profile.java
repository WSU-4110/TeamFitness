package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private Button settingsButton, accessibilityButton, notificationsButton, bioButton, returnHomeButton, logOutButton;
    private TextView nameTextView;

    private static final String PREFS_NAME = "BioPrefs";
    private static final String NAME_KEY = "name";
    private static final int REQUEST_BIO_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Initialize UI components
        settingsButton = findViewById(R.id.button);
        accessibilityButton = findViewById(R.id.button2);
        notificationsButton = findViewById(R.id.button3);
        bioButton = findViewById(R.id.button4);
        returnHomeButton = findViewById(R.id.HomeButton);
        logOutButton = findViewById(R.id.logOut); // Added logout button initialization
        nameTextView = findViewById(R.id.textView); // Initialize the TextView for the name

        // Load and display the saved name
        loadAndDisplayName();

        // Navigate to Settings
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Navigate to Accessibility
        accessibilityButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, AccessibilityActivity.class);
            startActivity(intent);
        });

        // Navigate to Notifications
        notificationsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, NotificationsActivity.class);
            startActivity(intent);
        });

        // Navigate to BioActivity
        bioButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, BioActivity.class);
            startActivityForResult(intent, REQUEST_BIO_ACTIVITY); // Start BioActivity and wait for a result
        });

        // Navigate back to HomeActivity
        returnHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        // Logout functionality
        logOutButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Load the name from SharedPreferences and display it in the TextView
    private void loadAndDisplayName() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString(NAME_KEY, "Default Name"); // Default to "Default Name"
        nameTextView.setText(name);
    }

    // Handle the result from BioActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_BIO_ACTIVITY && resultCode == RESULT_OK && data != null) {
            // Get the updated name and display it
            String updatedName = data.getStringExtra("updatedName");
            if (updatedName != null) {
                nameTextView.setText(updatedName);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the name when returning to this activity
        loadAndDisplayName();
    }
}
