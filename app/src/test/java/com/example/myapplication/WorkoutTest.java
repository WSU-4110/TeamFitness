package com.example.myapplication;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WorkoutTest {

    private Workout workout;

    @Before
    public void setUp() {
        workout = new Workout();
    }


    @Test
    public void testGetSetReps() {
        workout.setReps("15");
        assertEquals("Reps should be '15'", "15", workout.getReps());
    }

    @Test
    public void testGetSetSets() {
        workout.setSets("3");
        assertEquals("Sets should be '3'", "3", workout.getSets());
    }

    @Test
    public void testGetSetWeight() {
        workout.setWeight("20");
        assertEquals("Weight should be '20'", "20", workout.getWeight());
    }

    @Test
    public void testGetSetDistance() {
        workout.setDistance("5");
        assertEquals("Distance should be '5'", "5", workout.getDistance());
    }

    @Test
    public void testGetSetDuration() {
        workout.setDuration("30");
        assertEquals("Duration should be '30'", "30", workout.getDuration());
    }
}
