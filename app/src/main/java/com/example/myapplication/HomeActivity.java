// Updated HomeActivity.java
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myapplication.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FloatingActionButton fab, fab_workoutroutine, fab_post;
    private boolean isFabOpen = false;

    private DonutProgress circularProgress;
    private ProgressBar progressSteps, progressCalories, progressWeight;
    private TextView textStats;

    private DatabaseReference database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab_workoutroutine = findViewById(R.id.fab_workoutroutine);
        fab_post = findViewById(R.id.fab_post);

        circularProgress = findViewById(R.id.circular_progress);
        progressSteps = findViewById(R.id.progress_steps);
        progressCalories = findViewById(R.id.progress_calories);
        progressWeight = findViewById(R.id.progress_weight);
        textStats = findViewById(R.id.textStats);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        fab_workoutroutine.setVisibility(View.GONE);
        fab_post.setVisibility(View.GONE);

        fab.setOnClickListener(view -> toggleFABMenu());

        fab_workoutroutine.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, WorkoutRoutineCreationActivity.class);
            startActivity(intent);
            hideFABMenu();
        });

        fab_post.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, PostCreationActivity.class);
            startActivity(intent);
            hideFABMenu();
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_tracker, R.id.navigation_dashboard, R.id.navigation_progress)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        fetchUserData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_account) {
            Intent intent = new Intent(HomeActivity.this, Profile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleFABMenu() {
        if (isFabOpen) {
            hideFABMenu();
        } else {
            showFABMenu();
        }
    }

    private void showFABMenu() {
        fab_workoutroutine.setVisibility(View.VISIBLE);
        fab_post.setVisibility(View.VISIBLE);
        isFabOpen = true;
    }

    private void hideFABMenu() {
        fab_workoutroutine.setVisibility(View.GONE);
        fab_post.setVisibility(View.GONE);
        isFabOpen = false;
    }

    private void fetchUserData() {
        String userId = auth.getCurrentUser().getUid();
        database.child("users").child(userId).child("tables")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            int totalDistance = 0;
                            int totalDuration = 0;
                            int totalReps = 0;
                            int totalSets = 0;

                            for (DataSnapshot tableSnapshot : snapshot.getChildren()) {
                                if (tableSnapshot.child("Distance").exists()) {
                                    totalDistance += Integer.parseInt(tableSnapshot.child("Distance").getValue(String.class));
                                }
                                if (tableSnapshot.child("Duration").exists()) {
                                    totalDuration += Integer.parseInt(tableSnapshot.child("Duration").getValue(String.class));
                                }
                                if (tableSnapshot.child("Reps").exists()) {
                                    totalReps += Integer.parseInt(tableSnapshot.child("Reps").getValue(String.class));
                                }
                                if (tableSnapshot.child("Sets").exists()) {
                                    totalSets += Integer.parseInt(tableSnapshot.child("Sets").getValue(String.class));
                                }
                            }

                            updateUI(totalDistance, totalDuration, totalReps, totalSets);
                        } else {
                            Log.e("Firebase", "No workout data found.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.e("Firebase", "Error fetching data: " + error.getMessage());
                    }
                });
    }

    private void updateUI(int distance, int duration, int reps, int sets) {
        int totalProgress = (distance + duration + reps + sets) / 4;
        circularProgress.setProgress(totalProgress);

        progressSteps.setProgress(distance);
        progressCalories.setProgress(duration);
        progressWeight.setProgress(reps + sets);

        textStats.setText("Today's Workout Summary");
    }
}
