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

        workoutRoutineList = new ArrayList<>(); // Replace this with actual data from your database
        adapter = new WorkoutRoutineAdapter(this, workoutRoutineList);
        recyclerView.setAdapter(adapter);

        // Load data from the database or pass the data from another activity
    }
}
