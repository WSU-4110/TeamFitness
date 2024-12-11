package com.example.myapplication;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

public class SpecificWorkoutRoutineTest {
    private SpecificWorkoutRoutine specificWorkoutRoutine;

    @Before
    public void setUp() {
        specificWorkoutRoutine = new SpecificWorkoutRoutine();
    }

    @Test
    public void getWorkoutRoutineTitle() {
        specificWorkoutRoutine.setWorkoutRoutineTitle("Morning Routine");
        assertEquals("Morning Routine", specificWorkoutRoutine.getWorkoutRoutineTitle());
    }

    @Test
    public void setWorkoutRoutineTitle() {
        specificWorkoutRoutine.setWorkoutRoutineTitle("Evening Routine");
        assertEquals("Evening Routine", specificWorkoutRoutine.getWorkoutRoutineTitle());
    }

    @Test
    public void getWorkoutName() {
        specificWorkoutRoutine.setWorkoutName("Push-ups");
        assertEquals("Push-ups", specificWorkoutRoutine.getWorkoutName());
    }

    @Test
    public void setWorkoutName() {
        specificWorkoutRoutine.setWorkoutName("Squats");
        assertEquals("Squats", specificWorkoutRoutine.getWorkoutName());
    }

    @Test
    public void getReps() {
        specificWorkoutRoutine.setReps("15");
        assertEquals("15", specificWorkoutRoutine.getReps());
    }

    @Test
    public void setReps() {
        specificWorkoutRoutine.setReps("20");
        assertEquals("20", specificWorkoutRoutine.getReps());
    }

    @Test
    public void getSets() {
        specificWorkoutRoutine.setSets("3");
        assertEquals("3", specificWorkoutRoutine.getSets());
    }

    @Test
    public void setSets() {
        specificWorkoutRoutine.setSets("4");
        assertEquals("4", specificWorkoutRoutine.getSets());
    }

    @Test
    public void getWeight() {
        specificWorkoutRoutine.setWeight("50");
        assertEquals("50", specificWorkoutRoutine.getWeight());
    }

    @Test
    public void setWeight() {
        specificWorkoutRoutine.setWeight("60");
        assertEquals("60", specificWorkoutRoutine.getWeight());
    }

    @Test
    public void getDistance() {
        specificWorkoutRoutine.setDistance("2.5");
        assertEquals("2.5", specificWorkoutRoutine.getDistance());
    }

    @Test
    public void setDistance() {
        specificWorkoutRoutine.setDistance("3.0");
        assertEquals("3.0", specificWorkoutRoutine.getDistance());
    }

    @Test
    public void getDuration() {
        specificWorkoutRoutine.setDuration("30");
        assertEquals("30", specificWorkoutRoutine.getDuration());
    }

    @Test
    public void setDuration() {
        specificWorkoutRoutine.setDuration("45");
        assertEquals("45", specificWorkoutRoutine.getDuration());
    }
}


