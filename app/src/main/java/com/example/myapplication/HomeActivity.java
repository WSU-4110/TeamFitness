package com.example.myapplication;

import android.widget.ImageButton;
import android.view.View;
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
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    ImageButton imageButton; // Declare the button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Make sure you're using the right layout

        // Initialize the imageButton
        imageButton = findViewById(R.id.imageButton);

        // Set a click listener on the imageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Profile activity
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                startActivity(intent);
            }
        });  // Properly close the onClickListener here

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // Check if the profile button was clicked
        if (itemId == R.id.action_account) {
            // Navigate to the AccountActivity when the account button is clicked
            Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.toolbar) {
            // Navigate to ProfileActivity when the profile button is clicked
            Intent intent = new Intent(HomeActivity.this, Profile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
