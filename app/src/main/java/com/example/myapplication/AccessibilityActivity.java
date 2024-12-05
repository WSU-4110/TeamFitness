package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class AccessibilityActivity extends AppCompatActivity {

    // Constants for SharedPreferences
    private static final String PREFS_NAME = "AccessibilityPrefs";
    private static final String FONT_SIZE_KEY = "fontSize";
    private static final String THEME_KEY = "theme";
    private static final String HIGH_CONTRAST_KEY = "highContrast";

    private SeekBar fontSizeSeekBar;
    private RadioGroup themeRadioGroup;
    private Switch highContrastSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load saved theme
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int theme = getThemeFromPreferences(prefs);
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessibility);

        // Initialize UI components
        fontSizeSeekBar = findViewById(R.id.fontSizeSeekBar);
        themeRadioGroup = findViewById(R.id.themeRadioGroup);
        highContrastSwitch = findViewById(R.id.highContrastSwitch);
        Button saveButton = findViewById(R.id.saveAccessibilityButton);

        // Load saved settings
        loadSettings(prefs);

        // Save button listener
        saveButton.setOnClickListener(v -> {
            saveSettings(prefs);
            applySettings();
        });
    }

    private int getThemeFromPreferences(SharedPreferences prefs) {
        try {
            return prefs.getInt(THEME_KEY, R.style.LightTheme); // Default to LightTheme
        } catch (ClassCastException e) {
            // Clear incorrect value and default to LightTheme
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(THEME_KEY);
            editor.putInt(THEME_KEY, R.style.LightTheme);
            editor.apply();
            return R.style.LightTheme;
        }
    }

    private void loadSettings(SharedPreferences prefs) {
        // Load font size
        int fontSize = prefs.getInt(FONT_SIZE_KEY, 16); // Default is 16sp
        fontSizeSeekBar.setProgress(fontSize);

        // Load theme
        int savedTheme = getThemeFromPreferences(prefs);
        if (savedTheme == R.style.LightTheme) {
            themeRadioGroup.check(R.id.lightModeRadio);
        } else if (savedTheme == R.style.DarkTheme) {
            themeRadioGroup.check(R.id.darkModeRadio);
        }

        // Load high contrast
        boolean highContrast = prefs.getBoolean(HIGH_CONTRAST_KEY, false);
        highContrastSwitch.setChecked(highContrast);
    }

    private void saveSettings(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();

        // Save font size
        int fontSize = fontSizeSeekBar.getProgress();
        editor.putInt(FONT_SIZE_KEY, fontSize);

        // Save theme
        int selectedThemeId = themeRadioGroup.getCheckedRadioButtonId();
        if (selectedThemeId == R.id.lightModeRadio) {
            editor.putInt(THEME_KEY, R.style.LightTheme);
        } else if (selectedThemeId == R.id.darkModeRadio) {
            editor.putInt(THEME_KEY, R.style.DarkTheme);
        }

        // Save high contrast setting
        boolean highContrast = highContrastSwitch.isChecked();
        editor.putBoolean(HIGH_CONTRAST_KEY, highContrast);
        if (highContrast) {
            editor.putInt(THEME_KEY, R.style.HighContrastTheme);
        }

        editor.apply();
    }

    private void applySettings() {
        // Apply theme
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int theme = getThemeFromPreferences(prefs);
        setTheme(theme);

        // Apply font size to global configuration
        int fontSize = fontSizeSeekBar.getProgress();
        getResources().getConfiguration().fontScale = fontSize / 16f; // Adjust to scale
        getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());

        // Restart activity to apply changes
        recreate();
    }
}
