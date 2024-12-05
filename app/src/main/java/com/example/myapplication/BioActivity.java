package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class BioActivity extends AppCompatActivity {

    private EditText nameField, bioField;
    private ImageView profileImage;
    private Button saveBioButton;

    private static final String PREFS_NAME = "BioPrefs";
    private static final String NAME_KEY = "name";
    private static final String BIO_KEY = "bio";
    private static final String IMAGE_URI_KEY = "profileImageUri";

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bio);

        // Initialize UI components
        nameField = findViewById(R.id.nameField);
        bioField = findViewById(R.id.bioField);
        profileImage = findViewById(R.id.profileImage);
        saveBioButton = findViewById(R.id.saveBioButton);

        // Load saved bio data and profile picture
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadBioData(prefs);

        // Set listener for Save button
        saveBioButton.setOnClickListener(v -> {
            saveBioData(prefs);
            Toast.makeText(BioActivity.this, "Bio saved successfully!", Toast.LENGTH_SHORT).show();
            sendResultToProfile();
        });

        // Set listener for profile image click
        profileImage.setOnClickListener(v -> openImagePicker());
    }

    private void sendResultToProfile() {
        // Pass updated name back to ProfileActivity
        String updatedName = nameField.getText().toString().trim();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedName", updatedName);
        setResult(RESULT_OK, resultIntent);
        finish(); // Close BioActivity and return to ProfileActivity
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);

                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(IMAGE_URI_KEY, imageUri.toString());
                editor.apply();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadBioData(SharedPreferences prefs) {
        String name = prefs.getString(NAME_KEY, "");
        String bio = prefs.getString(BIO_KEY, "");
        nameField.setText(name);
        bioField.setText(bio);

        String imageUriString = prefs.getString(IMAGE_URI_KEY, null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            profileImage.setImageURI(imageUri);
        }
    }

    private void saveBioData(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(NAME_KEY, nameField.getText().toString().trim());
        editor.putString(BIO_KEY, bioField.getText().toString().trim());
        editor.apply();
    }
}
