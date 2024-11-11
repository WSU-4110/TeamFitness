package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WorkoutRoutineCreationActivity extends AppCompatActivity {
    private TextView labelSets, labelReps, labelDistance, labelDuration, labelWeight;
    private EditText inputSets, inputReps, inputDistance, inputDuration, inputWorkoutName, inputWeight;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    Button saveWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_routine_creation);

        labelSets = findViewById(R.id.label_sets);
        inputSets = findViewById(R.id.input_sets);
        labelReps = findViewById(R.id.label_reps);
        inputReps = findViewById(R.id.input_reps);
        labelWeight = findViewById(R.id.label_weight);
        inputWeight = findViewById(R.id.input_weight);
        labelDistance = findViewById(R.id.label_distance);
        inputDistance = findViewById(R.id.input_distance);
        labelDuration = findViewById(R.id.label_duration);
        inputDuration = findViewById(R.id.input_duration);
        inputWorkoutName = findViewById(R.id.input_workout_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Create Workout Routine");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        labelSets.setVisibility(View.GONE);
        inputSets.setVisibility(View.GONE);
        labelReps.setVisibility(View.GONE);
        inputReps.setVisibility(View.GONE);
        labelWeight.setVisibility(View.GONE);
        inputWeight.setVisibility(View.GONE);
        labelDistance.setVisibility(View.GONE);
        inputDistance.setVisibility(View.GONE);
        labelDuration.setVisibility(View.GONE);
        inputDuration.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if (view.getId() == R.id.cardio && checked) {
            labelSets.setVisibility(View.GONE);
            inputSets.setVisibility(View.GONE);
            labelReps.setVisibility(View.GONE);
            inputReps.setVisibility(View.GONE);
            labelWeight.setVisibility(View.GONE);
            inputWeight.setVisibility(View.GONE);
            labelDistance.setVisibility(View.VISIBLE);
            inputDistance.setVisibility(View.VISIBLE);
            labelDuration.setVisibility(View.VISIBLE);
            inputDuration.setVisibility(View.VISIBLE);

            // Initialize the save workout button and make the onClickListener
            saveWorkout = findViewById(R.id.button_save_workout);
            saveWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Get and save the inputs as strings
                    String strWorkoutName = inputWorkoutName.getText().toString();
                    String strDistance = inputDistance.getText().toString();
                    String strDuration = inputDuration.getText().toString();

                    // Get the current users ID to make sure that the inputs are personalized to them
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    // Gets the table ID of the table section in our Firebase database
                    String tableId = db.child("users").child(userId).child("tables").push().getKey();

                    // Creating a spot for the users inputs
                    Map<String, Object> tableData = new HashMap<>();

                    // Putting the users information into the hash
                    tableData.put("Workout Name", strWorkoutName);
                    tableData.put("Distance", strDistance);
                    tableData.put("Duration", strDuration);

                    // Creates a new table under the tables section in firebase
                    db.child("users").child(userId).child("tables").child(tableId)
                            .setValue(tableData)
                            .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                            .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));

                    Intent intent = new Intent(WorkoutRoutineCreationActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        } else if (view.getId() == R.id.weightLifting && checked) {
            labelSets.setVisibility(View.VISIBLE);
            inputSets.setVisibility(View.VISIBLE);
            labelReps.setVisibility(View.VISIBLE);
            inputReps.setVisibility(View.VISIBLE);
            labelWeight.setVisibility(View.VISIBLE);
            inputWeight.setVisibility(View.VISIBLE);
            labelDistance.setVisibility(View.GONE);
            inputDistance.setVisibility(View.GONE);
            labelDuration.setVisibility(View.GONE);
            inputDuration.setVisibility(View.GONE);


            saveWorkout = findViewById(R.id.button_save_workout);
            saveWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Getting the inputs and making them strings
                    String strWorkoutName = inputWorkoutName.getText().toString();
                    String strSets = inputSets.getText().toString();
                    String strReps = inputReps.getText().toString();
                    String strWeight = inputWeight.getText().toString();

                    // Gets the currents users ID so that only this user is able to see their input
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    // Gets the table id of the "tables" section in firebase
                    String tableId = db.child("users").child(userId).child("tables").push().getKey();

                    // Creates a spot to save the users information
                    Map<String, Object> tableData = new HashMap<>();

                    // Puts the users information into the hash
                    tableData.put("Workout Name", strWorkoutName);
                    tableData.put("Sets", strSets);
                    tableData.put("Reps", strReps);
                    tableData.put("Weight", strWeight);

                    // Creates a new table for the users inputs under the tables section in firebase
                    db.child("users").child(userId).child("tables").child(tableId)
                            .setValue(tableData)
                            .addOnSuccessListener(aVoid -> Log.d("RealTimeDB", "DocumentSnapshot successfully written!"))
                            .addOnFailureListener(e -> Log.w("RealTimeDB", "Error writing document", e));

                    Intent intent = new Intent(WorkoutRoutineCreationActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}
