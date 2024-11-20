package com.example.myapplication;

import org.junit.Test;
import static org.junit.Assert.*;

public class WorkoutTest {

    // Tests setting the workout name
    @Test
    public void setWorkoutName() {
        Workout workout = new Workout();
        String expectedName = "Bench Press";
        workout.setWorkoutName(expectedName);
        assertEquals("Check that the workout name was set correctly", expectedName, workout.getWorkoutName());
    }

    // Tests getting the workout name
    @Test
    public void getWorkoutName() {
        Workout workout = new Workout();
        String expectedName = "Pull Ups";
        workout.setWorkoutName(expectedName);
        assertEquals("Check that the workout name was retrieved correctly", expectedName, workout.getWorkoutName());
    }
}
