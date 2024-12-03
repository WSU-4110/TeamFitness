// fragment for piechart and barchart functionality
package com.example.myapplication.ui.tracker;

import android.app.AlertDialog;
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

    ProgressBar progressSteps;
    ProgressBar progressCalories;
    ProgressBar progressWeight;
    DonutProgress circularProgress;
    FirebaseAuth auth;
    DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        // Initialize progress bars and circular progress
        progressSteps = view.findViewById(R.id.progress_steps);
        progressCalories = view.findViewById(R.id.progress_calories);
        progressWeight = view.findViewById(R.id.progress_weight);
        circularProgress = view.findViewById(R.id.circular_progress);

        // Fetch initial max values and update UI
        fetchMaxValues();

        // Set click listeners for each progress bar
        progressSteps.setOnClickListener(v -> showDialog("Steps", progressSteps));
        progressCalories.setOnClickListener(v -> showDialog("Calories", progressCalories));
        progressWeight.setOnClickListener(v -> showDialog("Weight", progressWeight));

        return view;
    }

    void fetchMaxValues() {
        String userId = auth.getCurrentUser().getUid();
        database.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Fetch max values for each progress bar
                    Integer stepsMax = snapshot.child("stepsMax").getValue(Integer.class);
                    Integer caloriesMax = snapshot.child("caloriesMax").getValue(Integer.class);
                    Integer weightMax = snapshot.child("weightMax").getValue(Integer.class);

                    // Apply fetched max values to progress bars
                    if (stepsMax != null) progressSteps.setMax(stepsMax);
                    if (caloriesMax != null) progressCalories.setMax(caloriesMax);
                    if (weightMax != null) progressWeight.setMax(weightMax);

                    // Update the pie chart to reflect the new max values
                    updatePieChart();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching max values: " + error.getMessage());
            }
        });
    }

    void showDialog(String type, ProgressBar progressBar) {
        // Inflate the dialog layout
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_max, null);
        builder.setView(dialogView);

        TextView currentProgressText = dialogView.findViewById(R.id.current_progress_text);
        EditText inputNewMax = dialogView.findViewById(R.id.input_new_max);
        Button buttonConfirm = dialogView.findViewById(R.id.button_confirm);

        // Display current progress and max value
        int currentProgress = progressBar.getProgress();
        int currentMax = progressBar.getMax();
        currentProgressText.setText("Current Progress: " + currentProgress + "/" + currentMax);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Handle confirm button click
        buttonConfirm.setOnClickListener(v -> {
            String newMaxStr = inputNewMax.getText().toString();
            if (!newMaxStr.isEmpty()) {
                try {
                    int newMax = Integer.parseInt(newMaxStr);
                    progressBar.setMax(newMax);

                    // Update Firebase with the new max value
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

    void updatePieChart() {
        // Calculate the total progress and max values
        int totalProgress = progressSteps.getProgress() + progressCalories.getProgress() + progressWeight.getProgress();
        int totalMax = progressSteps.getMax() + progressCalories.getMax() + progressWeight.getMax();

        // Update the pie chart
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