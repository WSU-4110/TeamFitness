package com.example.myapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkoutRoutineTest {
    private WorkoutRoutine workoutRoutine;

    @BeforeEach
    void setUp() {
        workoutRoutine = new WorkoutRoutine();
    }

    @Test
    void getWorkoutRoutineTitle() {
        workoutRoutine.setWorkoutRoutineTitle("Morning Routine");
        assertEquals("Morning Routine", workoutRoutine.getWorkoutRoutineTitle());
    }

    @Test
    void setWorkoutRoutineTitle() {
        workoutRoutine.setWorkoutRoutineTitle("Evening Routine");
        assertEquals("Evening Routine", workoutRoutine.getWorkoutRoutineTitle());
    }

    @Test
    void getWorkoutName() {
        workoutRoutine.setWorkoutName("Push-ups");
        assertEquals("Push-ups", workoutRoutine.getWorkoutName());
    }

    @Test
    void setWorkoutName() {
        workoutRoutine.setWorkoutName("Squats");
        assertEquals("Squats", workoutRoutine.getWorkoutName());
    }

    @Test
    void getReps() {
        workoutRoutine.setReps(String.valueOf(15));
        assertEquals(15, workoutRoutine.getReps());
    }

    @Test
    void setReps() {
        workoutRoutine.setReps(String.valueOf(20));
        assertEquals(20, workoutRoutine.getReps());
    }

    @Test
    void getSets() {
        workoutRoutine.setSets(String.valueOf(3));
        assertEquals(3, workoutRoutine.getSets());
    }

    @Test
    void setSets() {
        workoutRoutine.setSets(String.valueOf(4));
        assertEquals(4, workoutRoutine.getSets());
    }

    @Test
    void getWeight() {
        workoutRoutine.setWeight(String.valueOf(50));
        assertEquals(50, workoutRoutine.getWeight());
    }

    @Test
    void setWeight() {
        workoutRoutine.setWeight(String.valueOf(60));
        assertEquals(60, workoutRoutine.getWeight());
    }

    @Test
    void getDistance() {
        workoutRoutine.setDistance(String.valueOf(2.5));
        assertEquals(2.5, workoutRoutine.getDistance());
    }

    @Test
    void setDistance() {
        workoutRoutine.setDistance(String.valueOf(3.0));
        assertEquals(3.0, workoutRoutine.getDistance());
    }

    @Test
    void getDuration() {
        workoutRoutine.setDuration(String.valueOf(30));
        assertEquals(30, workoutRoutine.getDuration());
    }

    @Test
    void setDuration() {
        workoutRoutine.setDuration(String.valueOf(45));
        assertEquals(45, workoutRoutine.getDuration());
    }
}
