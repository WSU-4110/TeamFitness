package com.example.myapplication.ui.tracker; // Add this if missing
import com.example.myapplication.R;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.myapplication.databinding.FragmentHomeBinding;

public class TrackerFragment extends Fragment {

    private FragmentHomeBinding binding;
    private int totalSteps = 10000;  // Assume the total goal for steps is 10,000

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Steps ProgressBar Click Listener
        binding.progressSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStepsPopup();
            }
        });

        // Calories ProgressBar Click Listener
        binding.progressCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenericPopup("Calories");
            }
        });

        // Weight ProgressBar Click Listener
        binding.progressWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenericPopup("Weight");
            }
        });

        // ImageButton Click Listener for Adding a New Trackable
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTrackablePopup();
            }
        });

        return root;
    }

    // Show popup for adding steps with amount, distance, time, and notes
    private void showStepsPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Steps");

        // Inflate custom layout for the popup
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_steps, null);
        builder.setView(dialogView);

        // Find input fields in the dialog
        final EditText inputAmount = dialogView.findViewById(R.id.input_amount);
        final EditText inputDistance = dialogView.findViewById(R.id.input_distance);
        final EditText inputTime = dialogView.findViewById(R.id.input_time);
        final EditText inputNotes = dialogView.findViewById(R.id.input_notes);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get user inputs
                String amountStr = inputAmount.getText().toString();
                int amount = Integer.parseInt(amountStr.isEmpty() ? "0" : amountStr);

                // Update Steps Progress Bar
                updateProgressBar(binding.progressSteps, amount, totalSteps);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    // Show generic popup for adding amount, time, and notes for calories or weight
    private void showGenericPopup(String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add " + type);

        // Inflate custom layout for the popup
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_generic, null);
        builder.setView(dialogView);

        final EditText inputAmount = dialogView.findViewById(R.id.input_amount);
        final EditText inputTime = dialogView.findViewById(R.id.input_time);
        final EditText inputNotes = dialogView.findViewById(R.id.input_notes);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get user inputs
                String amountStr = inputAmount.getText().toString();
                int amount = Integer.parseInt(amountStr.isEmpty() ? "0" : amountStr);

                // Handle progress update for Calories or Weight
                if (type.equals("Calories")) {
                    updateProgressBar(binding.progressCalories, amount, 2000); // Assuming 2000 as daily calorie goal
                } else if (type.equals("Weight")) {
                    updateProgressBar(binding.progressWeight, amount, 100); // Example: 100 as a weight target
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    // Method to update progress bars
    private void updateProgressBar(ProgressBar progressBar, int addedAmount, int totalAmount) {
        int currentProgress = progressBar.getProgress();
        int newProgress = currentProgress + (addedAmount * 100 / totalAmount);
        progressBar.setProgress(newProgress);
    }

    // Show popup for adding a new trackable
    private void showAddTrackablePopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Trackable");
        builder.setMessage("Would you like to add another trackable?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle adding another trackable
                Toast.makeText(getContext(), "New trackable added!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
