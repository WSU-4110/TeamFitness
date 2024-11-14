package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.ImageView;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PostCreationActivity extends AppCompatActivity {

    private LinearLayout workoutContainer;
    private Button buttonAddWorkout, buttonSaveWorkout;


    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    private EditText inputPostTitle, inputPostDescription;

    private ImageView postImage;


    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_creation);

        workoutContainer = findViewById(R.id.workout_container);
        buttonAddWorkout = findViewById(R.id.button_add_workout);
        buttonSaveWorkout = findViewById(R.id.button_save_workout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Create Workout Routine");
        }

        // Add initial workout form
        addWorkoutForm();

        buttonAddWorkout.setOnClickListener(v -> addWorkoutForm());


        buttonSaveWorkout.setOnClickListener(v -> saveWorkoutRoutine());

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
                String tableId = db.child("users").child(userId).child("WorkoutRoutines").push().getKey();

                // Creating a spot for the users inputs
                Map<String, Object> tableData = new HashMap<>();

                // Putting the users information into the hash
                tableData.put("Post Title", strTitle);
                tableData.put("Post Description", strDescription);

                // Creates a new table under the tables section in firebase
                db.child("users").child(userId).child("WorkoutRoutines").child(tableId)
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

    private void addWorkoutForm() {
        View workoutForm = LayoutInflater.from(this).inflate(R.layout.workout_form, workoutContainer, false);

        RadioGroup radioGroup = workoutForm.findViewById(R.id.radio_group_workout_type);
        View weightFields = workoutForm.findViewById(R.id.weight_fields);
        View cardioFields = workoutForm.findViewById(R.id.cardio_fields);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.cardio) {
                cardioFields.setVisibility(View.VISIBLE);
                weightFields.setVisibility(View.GONE);
            } else if (checkedId == R.id.weightLifting) {
                cardioFields.setVisibility(View.GONE);
                weightFields.setVisibility(View.VISIBLE);
            }
        });

        workoutContainer.addView(workoutForm);
    }

    private void saveWorkoutRoutine() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        for (int i = 0; i < workoutContainer.getChildCount(); i++) {
            View workoutForm = workoutContainer.getChildAt(i);

            EditText inputWorkoutName = workoutForm.findViewById(R.id.input_workout_name);
            String workoutName = inputWorkoutName.getText().toString();

            Map<String, Object> workoutData = new HashMap<>();
            workoutData.put("Workout Name", workoutName);

            RadioGroup radioGroup = workoutForm.findViewById(R.id.radio_group_workout_type);
            if (radioGroup.getCheckedRadioButtonId() == R.id.cardio) {
                EditText inputDistance = workoutForm.findViewById(R.id.input_distance);
                EditText inputDuration = workoutForm.findViewById(R.id.input_duration);
                workoutData.put("Distance", inputDistance.getText().toString());
                workoutData.put("Duration", inputDuration.getText().toString());
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.weightLifting) {
                EditText inputSets = workoutForm.findViewById(R.id.input_sets);
                EditText inputReps = workoutForm.findViewById(R.id.input_reps);
                EditText inputWeight = workoutForm.findViewById(R.id.input_weight);
                workoutData.put("Sets", inputSets.getText().toString());
                workoutData.put("Reps", inputReps.getText().toString());
                workoutData.put("Weight", inputWeight.getText().toString());
            }

            String tableId = db.child("users").child(userId).child("tables").push().getKey();
            db.child("users").child(userId).child("tables").child(tableId)
                    .setValue(workoutData)
                    .addOnSuccessListener(aVoid -> Log.d("RealTimeDB", "Workout added successfully!"))
                    .addOnFailureListener(e -> Log.w("RealTimeDB", "Error writing workout", e));
        }

        Intent intent = new Intent(PostCreationActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}