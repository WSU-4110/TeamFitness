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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FloatingActionButton fab, fab_workoutroutine, fab_post;
    private boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab_workoutroutine = findViewById(R.id.fab_workoutroutine);
        fab_post = findViewById(R.id.fab_post);

        fab_workoutroutine.setVisibility(View.GONE);
        fab_post.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFABMenu();
            }
        });

        fab_workoutroutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, WorkoutRoutineCreationActivity.class);
                startActivity(intent);
                hideFABMenu();
            }
        });

        fab_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PostCreationActivity.class);
                startActivity(intent);
                hideFABMenu();
            }
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
}
