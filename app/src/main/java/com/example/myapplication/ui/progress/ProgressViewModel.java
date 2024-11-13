package com.example.myapplication.ui.progress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.myapplication.Workout;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import android.util.Log;

public class ProgressViewModel extends ViewModel {
    private MutableLiveData<List<Workout>> workoutList = new MutableLiveData<>();
    private DatabaseReference databaseReference;

    public ProgressViewModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public LiveData<List<Workout>> getWorkoutList(String userId) {
        databaseReference.child("users").child(userId).child("tables")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Workout> workouts = new ArrayList<>();
                        for (DataSnapshot workoutSnapshot : snapshot.getChildren()) {
                            try {
                                String workoutName = workoutSnapshot.child("Workout Name").getValue(String.class);
                                String reps = workoutSnapshot.child("Reps").getValue(String.class);
                                String sets = workoutSnapshot.child("Sets").getValue(String.class);
                                String weight = workoutSnapshot.child("Weight").getValue(String.class);
                                String distance = workoutSnapshot.child("Distance").getValue(String.class);
                                String duration = workoutSnapshot.child("Duration").getValue(String.class);

                                Workout workout = new Workout();
                                workout.setWorkoutName(workoutName);
                                workout.setReps(reps);
                                workout.setSets(sets);
                                workout.setWeight(weight);
                                workout.setDistance(distance);
                                workout.setDuration(duration);

                                workouts.add(workout);
                                Log.i("ProgressViewModel", "Workout fetched: " + workoutName);
                            } catch (Exception e) {
                                Log.e("ProgressViewModel", "Error mapping workout: " + e.getMessage());
                            }
                        }

                        workoutList.setValue(workouts);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FirebaseError", "Error fetching workouts: " + error.getMessage());
                    }
                });
        return workoutList;
    }
}
