package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myapplication.databinding.ActivityHomeBinding;
import androidx.appcompat.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class HomeActivity extends AppCompatActivity implements TrackerObserver {

    private ActivityHomeBinding binding;
    private TrackerData trackerData;

    private ProgressBar progressSteps;
    private ProgressBar progressCalories;
    private ProgressBar progressWeight;

    private TextView stepsText;
    private TextView caloriesText;
    private TextView weightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        // Accessing fragment_home.xml elements
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentHome = fragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home);

        if (fragmentHome != null && fragmentHome.getView() != null) {
            progressSteps = fragmentHome.getView().findViewById(R.id.progress_steps);
            progressCalories = fragmentHome.getView().findViewById(R.id.progress_calories);
            progressWeight = fragmentHome.getView().findViewById(R.id.progress_weight);

            stepsText = fragmentHome.getView().findViewById(R.id.stepsText);
            caloriesText = fragmentHome.getView().findViewById(R.id.caloriesText);
            weightText = fragmentHome.getView().findViewById(R.id.weightText);
        }

        // start TrackerData and add this activity as an observer
        trackerData = new TrackerData();
        trackerData.addObserver(this);

        // Example data update
        trackerData.setSteps(5000);
        trackerData.setCalories(300);
        trackerData.setWeight(150);
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

    // Update UI components if data changes
    @Override
    public void update(int steps, int calories, int weight) {
        // Update UI components when data changes
        if (progressSteps != null && stepsText != null) {
            progressSteps.setProgress(steps);
            stepsText.setText("Steps: " + steps);
        }

        if (progressCalories != null && caloriesText != null) {
            progressCalories.setProgress(calories);
            caloriesText.setText("Calories: " + calories);
        }

        if (progressWeight != null && weightText != null) {
            progressWeight.setProgress(weight);
            weightText.setText("Weight: " + weight);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trackerData.removeObserver(this);
    }
}
