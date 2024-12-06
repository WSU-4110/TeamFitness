package com.example.myapplication.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.WorkoutAdapter;
import com.example.myapplication.R;
import android.widget.TextView;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;

public class ProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView emptyProgress;
    private WorkoutAdapter workoutAdapter;
    private ProgressViewModel progressViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_progress, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        emptyProgress = root.findViewById(R.id.emptyProgress);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        workoutAdapter = new WorkoutAdapter(new ArrayList<>());
        recyclerView.setAdapter(workoutAdapter);

        progressViewModel = new ViewModelProvider(this).get(ProgressViewModel.class);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            progressViewModel.getWorkoutList(userId).observe(getViewLifecycleOwner(), workouts -> {
                if (workouts != null && !workouts.isEmpty()) {
                    workoutAdapter.updateWorkoutList(workouts);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyProgress.setVisibility(View.GONE);
                    Log.i("ProgressFragment", "Workouts updated in RecyclerView.");
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyProgress.setVisibility(View.VISIBLE);
                    Log.i("ProgressFragment", "No workouts found.");
                }
            });
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyProgress.setVisibility(View.VISIBLE);
            Log.e("ProgressFragment", "User not authenticated. Cannot fetch workouts.");
        }

        return root;
    }
}
