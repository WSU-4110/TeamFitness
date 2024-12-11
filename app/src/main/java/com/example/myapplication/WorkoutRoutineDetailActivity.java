package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkoutRoutineDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SpecificWorkoutRoutineAdapter adapter;
    private List<SpecificWorkoutRoutine> specificWorkoutRoutineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workoutroutine_detail);

        recyclerView = findViewById(R.id.workout_routine_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the specificWorkoutRoutineList
        specificWorkoutRoutineList = new ArrayList<>();

        // Get the title of the selected workout routine from the intent
        String routineTitle = getIntent().getStringExtra("title");

        if (routineTitle != null) {
            loadWorkoutRoutineDetails(routineTitle);
        } else {
            Log.e("WorkoutRoutineDetailActivity", "Routine title is null");
        }

        // Set up the adapter with the populated specificWorkoutRoutineList
        adapter = new SpecificWorkoutRoutineAdapter(this, specificWorkoutRoutineList);
        recyclerView.setAdapter(adapter);
    }

    private void loadWorkoutRoutineDetails(String routineTitle) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("routines");

        dbRef.orderByChild("Routine Name").equalTo(routineTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot routineSnapshot : dataSnapshot.getChildren()) {
                        // Fetch workouts under the selected routine
                        DataSnapshot workoutsSnapshot = routineSnapshot.child("Workouts");
                        for (DataSnapshot workoutSnapshot : workoutsSnapshot.getChildren()) {
                            String workoutName = workoutSnapshot.child("Workout Name").getValue(String.class);
                            String duration = workoutSnapshot.child("Duration").getValue(String.class);
                            String distance = workoutSnapshot.child("Distance").getValue(String.class);
                            String weight = workoutSnapshot.child("Weight").getValue(String.class);
                            String sets = workoutSnapshot.child("Sets").getValue(String.class);
                            String reps = workoutSnapshot.child("Reps").getValue(String.class);

                            SpecificWorkoutRoutine specificWorkoutRoutine = new SpecificWorkoutRoutine(
                                    workoutName != null ? workoutName : "",
                                    duration != null ? duration : "",
                                    distance != null ? distance : "",
                                    weight != null ? weight : "",
                                    sets != null ? sets : "",
                                    reps != null ? reps : ""
                            );

                            specificWorkoutRoutineList.add(specificWorkoutRoutine);
                        }
                    }
                    // Notify adapter that the data has changed
                    adapter.notifyDataSetChanged();
                } else {
                    Log.w("WorkoutRoutineDetailActivity", "No routine found with the given title: " + routineTitle);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("WorkoutRoutineDetailActivity", "loadWorkoutRoutineDetails:onCancelled", databaseError.toException());
            }
        });
    }
}
