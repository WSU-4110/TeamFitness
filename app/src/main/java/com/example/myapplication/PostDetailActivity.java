package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkoutRoutineAdapter adapter;
    private List<WorkoutRoutine> workoutRoutineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        recyclerView = findViewById(R.id.workout_routine_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the workoutRoutineList with placeholder data
        workoutRoutineList = new ArrayList<>();

        // Add the first workout routine
        workoutRoutineList.add(new WorkoutRoutine(
                getString(R.string.workout_name_1),
                getString(R.string.duration_1),
                getString(R.string.distance_1),
                getString(R.string.weight_1),
                getString(R.string.sets_1),
                getString(R.string.reps_1)
        ));

        // Add the second workout routine
        workoutRoutineList.add(new WorkoutRoutine(
                getString(R.string.workout_name_2),
                getString(R.string.duration_2),
                getString(R.string.distance_2),
                getString(R.string.weight_2),
                getString(R.string.sets_2),
                getString(R.string.reps_2)
        ));

        // Add the third workout routine
        workoutRoutineList.add(new WorkoutRoutine(
                getString(R.string.workout_name_3),
                getString(R.string.duration_3),
                getString(R.string.distance_3),
                getString(R.string.weight_3),
                getString(R.string.sets_3),
                getString(R.string.reps_3)
        ));

        // Set up the adapter with the populated workoutRoutineList
        adapter = new WorkoutRoutineAdapter(this, workoutRoutineList);
        recyclerView.setAdapter(adapter);

        // Load data from the database or pass the data from another activity
    }
}
