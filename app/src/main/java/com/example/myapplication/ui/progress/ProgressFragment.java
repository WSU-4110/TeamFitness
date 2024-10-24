package com.example.myapplication.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class ProgressFragment extends Fragment {

    private DatabaseReference databaseReference;
    private EditText inputNumber;
    private Button buttonSave;
    private TextView textProgress;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        // Initialize Firebase Auth and Database
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // Reference to current user's data in Firebase Database
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("numbers");
        } else {
            // Handle case when user is not logged in (if needed)
            Toast.makeText(getContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Initialize UI components
        inputNumber = view.findViewById(R.id.input_number);
        buttonSave = view.findViewById(R.id.button_save);
        textProgress = view.findViewById(R.id.text_progress);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Setup RecyclerView (add your adapter later)
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load user's saved number when fragment is created
        loadSavedNumber();

        // Set up Save button click listener
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNumberToFirebase();
            }
        });

        return view;
    }

    private void saveNumberToFirebase() {
        String number = inputNumber.getText().toString().trim();

        if (!number.isEmpty()) {
            // Save the number under the user's unique ID
            databaseReference.setValue(number)
                    .addOnSuccessListener(aVoid -> {
                        // Update the progress text and show a confirmation message
                        textProgress.setText("Last saved number: " + number);
                        Toast.makeText(getContext(), "Number saved!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Failed to save number: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSavedNumber() {
        // Load the saved number from Firebase for the current user
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String savedNumber = dataSnapshot.getValue(String.class);
                if (savedNumber != null) {
                    textProgress.setText("Last saved number: " + savedNumber);
                } else {
                    textProgress.setText("No number saved yet.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Toast.makeText(getContext(), "Failed to load data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}



