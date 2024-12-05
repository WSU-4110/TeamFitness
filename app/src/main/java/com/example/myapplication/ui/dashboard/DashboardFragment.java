package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;
import com.example.myapplication.ui.PostsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView mRecyclerView;
    private List<String> mRoutineTitles;
    private PostsAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the RecyclerView.
        mRecyclerView = root.findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the ArrayList that will contain the data.
        mRoutineTitles = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new PostsAdapter(getContext(), mRoutineTitles);
        mRecyclerView.setAdapter(mAdapter);

        // Fetch data from Firebase.
        fetchWorkoutRoutines();

        return root;
    }

    private void fetchWorkoutRoutines() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userId).child("routines");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRoutineTitles.clear();
                for (DataSnapshot routineSnapshot : dataSnapshot.getChildren()) {
                    String routineName = routineSnapshot.child("Routine Name").getValue(String.class);
                    if (routineName != null) {
                        mRoutineTitles.add(routineName);
                    }
                }
                // Notify adapter that the data has changed.
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("DashboardFragment", "loadWorkoutRoutines:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
