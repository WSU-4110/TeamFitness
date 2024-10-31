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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// ProgressFragment set as Observer of ViewModel
public class ProgressFragment extends Fragment implements Observer {

    private DatabaseReference databaseReference;
    private EditText inputNumber;
    private Button buttonSave;
    private TextView textProgress;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private ProgressViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Get Layout
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        // Initialize Firebase and Get User Data
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // Get Users Number
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("numbers");
        } else {
            //No User Logged In
            Toast.makeText(getContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
            return view;
        }

        // UI Stuff
        inputNumber = view.findViewById(R.id.input_number);
        buttonSave = view.findViewById(R.id.button_save);
        textProgress = view.findViewById(R.id.text_progress);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        buttonSave.setOnClickListener(v -> saveNumberToFirebase());
        loadSavedNumber();

        // Observer Setup
        viewModel = new ViewModelProvider(this).get(ProgressViewModel.class);
        viewModel.registerObserver(this);

        return view;
    }

    @Override // Destroy Observer
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.removeObserver(this);
    }


    @Override //Update Observer
    public void update() {
        loadSavedNumber();
    }

    //Save Number Under User Id And Write To Firebase
    private void saveNumberToFirebase() {
        String number = inputNumber.getText().toString().trim();
        if (!number.isEmpty()) {
            databaseReference.setValue(number)
                    .addOnSuccessListener(aVoid -> {
                        textProgress.setText("Last saved number: " + number);
                        Toast.makeText(getContext(), "Number saved", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to save number: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
        }
    }

    //Load Saved Number And Write To Firebase
    private void loadSavedNumber() {
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
