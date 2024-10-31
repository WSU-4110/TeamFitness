package com.example.myapplication.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {
    private ProfileViewModel profileViewModel;
    private TextView nameTextView;
    private TextView bioTextView;
    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile); // Ensure this layout file exists and matches your structure

        // Initialize UI components
        nameTextView = findViewById(R.id.textView);
        bioTextView = findViewById(R.id.textView2);
        profileImageView = findViewById(R.id.imageView2);

        // Initialize the ViewModel
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Observe the profile data
        profileViewModel.getProfileData().observe(this, profile -> {
            if (profile != null) {
                nameTextView.setText(profile.getName());
                bioTextView.setText(profile.getBio());
                Glide.with(this).load(profile.getProfilePictureUrl()).into(profileImageView);
            }
        });

        // Load profile data
        profileViewModel.loadProfile();
    }
}
