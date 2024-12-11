package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutRoutineCreationActivity extends AppCompatActivity {

    private LinearLayout workoutContainer;
    private Button buttonAddWorkout, buttonSaveWorkout;
    private EditText inputRoutineTitle;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workoutroutine_creation);

        workoutContainer = findViewById(R.id.workout_container);
        buttonAddWorkout = findViewById(R.id.button_add_workout);
        buttonSaveWorkout = findViewById(R.id.button_save_workout);
        inputRoutineTitle = findViewById(R.id.input_workout_routine_title); // Corrected ID

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
        String routineName = inputRoutineTitle.getText().toString().trim(); // Corrected variable

        if (routineName.isEmpty()) {
            inputRoutineTitle.setError("Routine name is required");
            inputRoutineTitle.requestFocus();
            return;
        }

        List<Map<String, Object>> workouts = new ArrayList<>();

        for (int i = 0; i < workoutContainer.getChildCount(); i++) {
            View workoutForm = workoutContainer.getChildAt(i);

            EditText inputWorkoutName = workoutForm.findViewById(R.id.input_workout_name);
            String workoutName = inputWorkoutName.getText().toString();

            if (workoutName.isEmpty()) continue;

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

            workouts.add(workoutData);
        }

        if (workouts.isEmpty()) {
            Log.w("WorkoutRoutineCreationActivity", "No workouts to save");
            return;
        }

        Map<String, Object> routineData = new HashMap<>();
        routineData.put("Routine Name", routineName);
        routineData.put("Workouts", workouts);

        String routineId = db.child("users").child(userId).child("routines").push().getKey();
        if (routineId != null) {
            db.child("users").child(userId).child("routines").child(routineId)
                    .setValue(routineData)
                    .addOnSuccessListener(aVoid -> Log.d("RealTimeDB", "Routine added successfully!"))
                    .addOnFailureListener(e -> Log.w("RealTimeDB", "Error writing routine", e));
        }

        Intent intent = new Intent(WorkoutRoutineCreationActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
