package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PostCreationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;

    private ImageView postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_creation);

        postImage = findViewById(R.id.postImage);
        Button addPhotoButton = findViewById(R.id.addPhotoButton);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addPhotoButton.setOnClickListener(view -> showImagePickerDialog());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showImagePickerDialog() {
        // This method should show a dialog to choose between camera or gallery
        // For simplicity, we will just open the gallery directly here
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE && data != null) {
                Uri selectedImage = data.getData();
                postImage.setImageURI(selectedImage);
                postImage.setVisibility(View.VISIBLE); // Set visibility to VISIBLE here
            } else if (requestCode == CAPTURE_IMAGE && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                postImage.setImageBitmap(photo);
                postImage.setVisibility(View.VISIBLE); // Set visibility to VISIBLE here
            }
        }
    }

    public void addImagePost(View view) {
        // For simplicity, this method opens the gallery
        showImagePickerDialog();
        postImage.setVisibility(View.VISIBLE);
    }
}
