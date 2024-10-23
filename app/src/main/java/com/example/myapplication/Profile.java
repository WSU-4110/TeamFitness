package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    Button settingsButton, logOutButton, personalInfoButton,
            notificationsButton, bioButton, returnHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        settingsButton = findViewById(R.id.button);
        logOutButton = findViewById(R.id.logOut);
        personalInfoButton = findViewById(R.id.button3);
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

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        personalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, PersonalInformation.class);
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
