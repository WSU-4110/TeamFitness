package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PostCreationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;

    private EditText inputPostTitle, inputPostDescription;

    private ImageView postImage;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    Button submitButton;

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

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                inputPostTitle = findViewById(R.id.postTitle);
                inputPostDescription = findViewById(R.id.postDescription);

                String strTitle = inputPostTitle.getText().toString();
                String strDescription = inputPostDescription.getText().toString();

                // Get the current users ID to make sure that the inputs are personalized to them
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Gets the table ID of the table section in our Firebase database
                String tableId = db.child("users").child(userId).child("tables").push().getKey();

                // Creating a spot for the users inputs
                Map<String, Object> tableData = new HashMap<>();

                // Putting the users information into the hash
                tableData.put("Post Title", strTitle);
                tableData.put("Post Description", strDescription);

                // Creates a new table under the tables section in firebase
                db.child("users").child(userId).child("tables").child(tableId)
                        .setValue(tableData)
                        .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                        .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));

                Intent intent = new Intent(PostCreationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
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