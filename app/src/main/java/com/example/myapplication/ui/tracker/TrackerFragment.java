// Organized TrackerFragment.java
package com.example.myapplication.ui.tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackerFragment extends Fragment {

    // UI Elements
    private ProgressBar progressSteps;
    private ProgressBar progressCalories;
    private ProgressBar progressWeight;
    private DonutProgress circularProgress;

    // Firebase Instances
    private FirebaseAuth auth;
    private DatabaseReference database;

    // Lifecycle Methods
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeFirebase();
        initializeUI(view);
        fetchMaxValues();
        setClickListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMaxValuesFromPreferences();
        updatePieChart();
    }

    // Initialization Methods
    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
    }

    private void initializeUI(View view) {
        progressSteps = view.findViewById(R.id.progress_steps);
        progressCalories = view.findViewById(R.id.progress_calories);
        progressWeight = view.findViewById(R.id.progress_weight);
        circularProgress = view.findViewById(R.id.circular_progress);
    }

    private void setClickListeners() {
        progressSteps.setOnClickListener(v -> showDialog("Steps", progressSteps));
        progressCalories.setOnClickListener(v -> showDialog("Calories", progressCalories));
        progressWeight.setOnClickListener(v -> showDialog("Weight", progressWeight));
    }

    // Data Fetching and UI Updates
    private void fetchMaxValues() {
        String userId = auth.getCurrentUser().getUid();
        database.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    saveMaxValuesToPreferences(snapshot);
                    updateProgressBars(snapshot);
                    updatePieChart();
                } else {
                    Log.e("Firebase", "No max values found for the user.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching max values: " + error.getMessage());
            }
        });
    }

    private void saveMaxValuesToPreferences(DataSnapshot snapshot) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Integer stepsMax = snapshot.child("stepsMax").getValue(Integer.class);
        Integer caloriesMax = snapshot.child("caloriesMax").getValue(Integer.class);
        Integer weightMax = snapshot.child("weightMax").getValue(Integer.class);

        if (stepsMax != null) editor.putInt("stepsMax", stepsMax);
        if (caloriesMax != null) editor.putInt("caloriesMax", caloriesMax);
        if (weightMax != null) editor.putInt("weightMax", weightMax);

        editor.apply();
    }

    private void updateProgressBars(DataSnapshot snapshot) {
        Integer stepsMax = snapshot.child("stepsMax").getValue(Integer.class);
        Integer caloriesMax = snapshot.child("caloriesMax").getValue(Integer.class);
        Integer weightMax = snapshot.child("weightMax").getValue(Integer.class);

        if (stepsMax != null) progressSteps.setMax(stepsMax);
        if (caloriesMax != null) progressCalories.setMax(caloriesMax);
        if (weightMax != null) progressWeight.setMax(weightMax);
    }

    private void loadMaxValuesFromPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        int stepsMax = sharedPreferences.getInt("stepsMax", 10000); // Default to 10000
        int caloriesMax = sharedPreferences.getInt("caloriesMax", 2000); // Default to 2000
        int weightMax = sharedPreferences.getInt("weightMax", 100); // Default to 100

        progressSteps.setMax(stepsMax);
        progressCalories.setMax(caloriesMax);
        progressWeight.setMax(weightMax);
    }

    // Dialog and Input Handling
    private void showDialog(String type, ProgressBar progressBar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_max, null);
        builder.setView(dialogView);

        TextView currentProgressText = dialogView.findViewById(R.id.current_progress_text);
        EditText inputNewMax = dialogView.findViewById(R.id.input_new_max);
        Button buttonConfirm = dialogView.findViewById(R.id.button_confirm);

        int currentProgress = progressBar.getProgress();
        int currentMax = progressBar.getMax();
        currentProgressText.setText("Current Progress: " + currentProgress + "/" + currentMax);

        AlertDialog dialog = builder.create();
        dialog.show();

        buttonConfirm.setOnClickListener(v -> {
            String newMaxStr = inputNewMax.getText().toString();
            if (!newMaxStr.isEmpty()) {
                try {
                    int newMax = Integer.parseInt(newMaxStr);
                    progressBar.setMax(newMax);

                    String userId = auth.getCurrentUser().getUid();
                    database.child("users").child(userId).child(type.toLowerCase() + "Max").setValue(newMax)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Max value updated successfully", Toast.LENGTH_SHORT).show();
                                updatePieChart();
                                dialog.dismiss();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Failed to update max value", Toast.LENGTH_SHORT).show();
                                Log.e("Firebase", "Error updating max value", e);
                            });
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid number entered", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Chart Updates
    private void updatePieChart() {
        int totalProgress = progressSteps.getProgress() + progressCalories.getProgress() + progressWeight.getProgress();
        int totalMax = progressSteps.getMax() + progressCalories.getMax() + progressWeight.getMax();

        if (totalMax > 0) {
            float percentage = (totalProgress / (float) totalMax) * 100;
            circularProgress.setMax(100);
            circularProgress.setProgress((int) percentage);
        } else {
            circularProgress.setMax(100);
            circularProgress.setProgress(0);
        }
    }
}
