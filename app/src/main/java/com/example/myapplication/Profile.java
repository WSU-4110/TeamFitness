package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    Button settingsButton, accessibilityButton, personalInfoButton, notificationsButton, bioButton, returnHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        settingsButton = findViewById(R.id.button);
        accessibilityButton = findViewById(R.id.button2);
        notificationsButton = findViewById(R.id.button4);
        bioButton = findViewById(R.id.button5);
        returnHomeButton = findViewById(R.id.HomeButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        accessibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, AccessibilityActivity.class);
                startActivity(intent);
            }
        });


        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Profile.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });

        bioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Profile.this, BioActivity.class);
                startActivity(intent);
            }
        });

        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Profile.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
