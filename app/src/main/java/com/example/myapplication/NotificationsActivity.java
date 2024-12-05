package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class NotificationsActivity extends AppCompatActivity {

    private TextView notificationHeader, notificationMessage, emptyText;
    private ImageView emptyIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        // Initialize UI components
        notificationHeader = findViewById(R.id.notificationHeader);
        notificationMessage = findViewById(R.id.notificationMessage);
        emptyIcon = findViewById(R.id.emptyIcon);
        emptyText = findViewById(R.id.emptyText);

        // Retrieve the notification details or progress message from the Intent
        String congratsMessage = getIntent().getStringExtra("congratsMessage");

        if (congratsMessage != null) {
            // Replace the empty state with the congratulatory message
            displayCongratsMessage(congratsMessage);
        } else {
            // Show the default empty state
            showEmptyState();
        }
    }


    private void displayCongratsMessage(String message) {
        // Set the notification header
        notificationHeader.setText("Congratulations!");

        // Set the congratulatory message
        notificationMessage.setVisibility(View.VISIBLE);
        notificationMessage.setText(message);

        // Hide the empty state views
        emptyIcon.setVisibility(View.GONE);
        emptyText.setVisibility(View.GONE);
    }

    private void showNotification(String title, String message) {
        // Set the notification header
        notificationHeader.setText(title);

        // Set the message dynamically
        notificationMessage.setVisibility(View.VISIBLE);
        notificationMessage.setText(message);

        // Hide the empty state views
        emptyIcon.setVisibility(View.GONE);
        emptyText.setVisibility(View.GONE);
    }

    private void showEmptyState() {
        // Default empty state
        emptyIcon.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.VISIBLE);
        notificationMessage.setVisibility(View.GONE);
    }
}
