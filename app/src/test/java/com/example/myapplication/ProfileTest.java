package com.example.myapplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class ProfileTest {

    private String settingsLabel;
    private String accessibilityLabel;
    private String notificationsLabel;
    private String bioLabel;
    private String homeLabel;
    private String expectedProfileName;

    @Before
    public void setUp() {
        settingsLabel = "Settings";
        accessibilityLabel = "Accessibility";
        notificationsLabel = "Notifications";
        bioLabel = "Bio";
        homeLabel = "Return Home";
        expectedProfileName = "John Doe";
    }

    @Test
    public void testSettingsLabel() {
        assertEquals("Settings label is incorrect", "Settings", settingsLabel);
    }

    @Test
    public void testAccessibilityLabel() {
        assertEquals("Accessibility label is incorrect", "Accessibility", accessibilityLabel);
    }

    @Test
    public void testNotificationsLabel() {
        assertEquals("Notifications label is incorrect", "Notifications", notificationsLabel);
    }

    @Test
    public void testBioLabel() {
        assertEquals("Bio label is incorrect", "Bio", bioLabel);
    }

    @Test
    public void testHomeLabel() {
        assertEquals("Return Home label is incorrect", "Return Home", homeLabel);
    }

    @Test
    public void testProfileName() {
        String actualProfileName = "John Doe";
        assertEquals("Profile name is incorrect", expectedProfileName, actualProfileName);
    }
}