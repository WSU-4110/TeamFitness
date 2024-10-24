package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WorkoutRoutineCreationActivity extends AppCompatActivity {
    private TextView labelSets, labelReps, labelDistance;
    private EditText inputSets, inputReps, inputDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_routine_creation);

        labelSets = findViewById(R.id.label_sets);
        inputSets = findViewById(R.id.input_sets);
        labelReps = findViewById(R.id.label_reps);
        inputReps = findViewById(R.id.input_reps);
        labelDistance = findViewById(R.id.label_distance);
        inputDistance = findViewById(R.id.input_distance);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if (view.getId() == R.id.cardio && checked) {
            labelSets.setVisibility(View.GONE);
            inputSets.setVisibility(View.GONE);
            labelReps.setVisibility(View.GONE);
            inputReps.setVisibility(View.GONE);
        } else if (view.getId() == R.id.weightLifting && checked) {
            labelSets.setVisibility(View.VISIBLE);
            inputSets.setVisibility(View.VISIBLE);
            labelReps.setVisibility(View.VISIBLE);
            inputReps.setVisibility(View.VISIBLE);
            labelDistance.setVisibility(View.GONE);
            inputDistance.setVisibility(View.GONE);
        }


    }

}