package com.example.myapplication;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class WorkoutRoutineTest {
    private WorkoutRoutine workoutRoutine;

    @Before
    public void setUp() {
        workoutRoutine = new WorkoutRoutine();
    }

    @Test
    public void getWorkoutRoutineTitle() {
        workoutRoutine.setWorkoutRoutineTitle("Morning Routine");
        assertEquals("Morning Routine", workoutRoutine.getWorkoutRoutineTitle());
    }

    @Test
    public void setWorkoutRoutineTitle() {
        workoutRoutine.setWorkoutRoutineTitle("Evening Routine");
        assertEquals("Evening Routine", workoutRoutine.getWorkoutRoutineTitle());
    }

    @Test
    public void getWorkoutName() {
        workoutRoutine.setWorkoutName("Push-ups");
        assertEquals("Push-ups", workoutRoutine.getWorkoutName());
    }

    @Test
    public void setWorkoutName() {
        workoutRoutine.setWorkoutName("Squats");
        assertEquals("Squats", workoutRoutine.getWorkoutName());
    }

    @Test
    public void getReps() {
        workoutRoutine.setReps("15");
        assertEquals("15", workoutRoutine.getReps());
    }

    @Test
    public void setReps() {
        workoutRoutine.setReps("20");
        assertEquals("20", workoutRoutine.getReps());
    }

    @Test
    public void getSets() {
        workoutRoutine.setSets("3");
        assertEquals("3", workoutRoutine.getSets());
    }

    @Test
    public void setSets() {
        workoutRoutine.setSets("4");
        assertEquals("4", workoutRoutine.getSets());
    }

    @Test
    public void getWeight() {
        workoutRoutine.setWeight("50");
        assertEquals("50", workoutRoutine.getWeight());
    }

    @Test
    public void setWeight() {
        workoutRoutine.setWeight("60");
        assertEquals("60", workoutRoutine.getWeight());
    }

    @Test
    public void getDistance() {
        workoutRoutine.setDistance("2.5");
        assertEquals("2.5", workoutRoutine.getDistance());
    }

    @Test
    public void setDistance() {
        workoutRoutine.setDistance("3.0");
        assertEquals("3.0", workoutRoutine.getDistance());
    }

    @Test
    public void getDuration() {
        workoutRoutine.setDuration("30");
        assertEquals("30", workoutRoutine.getDuration());
    }

    @Test
    public void setDuration() {
        workoutRoutine.setDuration("45");
        assertEquals("45", workoutRoutine.getDuration());
    }
}


