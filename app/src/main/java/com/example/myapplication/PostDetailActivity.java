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

        // Initialize the workoutRoutineList
        workoutRoutineList = new ArrayList<>();

        // Get the title of the selected workout routine from the intent
        String routineTitle = getIntent().getStringExtra("title");

        if (routineTitle != null) {
            loadWorkoutRoutineDetails(routineTitle);
        } else {
            Log.e("PostDetailActivity", "Routine title is null");
        }

        // Set up the adapter with the populated workoutRoutineList
        adapter = new WorkoutRoutineAdapter(this, workoutRoutineList);
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

                            WorkoutRoutine workoutRoutine = new WorkoutRoutine(
                                    workoutName != null ? workoutName : "",
                                    duration != null ? duration : "",
                                    distance != null ? distance : "",
                                    weight != null ? weight : "",
                                    sets != null ? sets : "",
                                    reps != null ? reps : ""
                            );

                            workoutRoutineList.add(workoutRoutine);
                        }
                    }
                    // Notify adapter that the data has changed
                    adapter.notifyDataSetChanged();
                } else {
                    Log.w("PostDetailActivity", "No routine found with the given title: " + routineTitle);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PostDetailActivity", "loadWorkoutRoutineDetails:onCancelled", databaseError.toException());
            }
        });
    }
}
