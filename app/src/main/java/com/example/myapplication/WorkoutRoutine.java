package com.example.myapplication;
import android.util.Log;

public class WorkoutRoutine {
    private String workoutRoutineTitle;
    private String workoutName;
    private String reps;
    private String sets;
    private String weight;
    private String distance;
    private String duration;

    // Parameterized constructor
    public WorkoutRoutine(String workoutName, String duration, String distance, String weight, String sets, String reps) {
        this.workoutName = workoutName;
        this.duration = duration;
        this.distance = distance;
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
    }

    // Default constructor
    public WorkoutRoutine() {}

    // Getters and Setters
    public String getWorkoutRoutineTitle() { return workoutRoutineTitle; }
    public void setWorkoutRoutineTitle(String workoutRoutineTitle) { this.workoutRoutineTitle = workoutRoutineTitle; }

    public String getWorkoutName() { return workoutName; }
    public void setWorkoutName(String workoutName) { this.workoutName = workoutName; }

    public String getReps() { return reps; }
    public void setReps(String reps) { this.reps = reps; }

    public String getSets() { return sets; }
    public void setSets(String sets) { this.sets = sets; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getDistance() { return distance; }
    public void setDistance(String distance) { this.distance = distance; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }



    public int calculateSteps() {
        try {
            return (distance != null && !distance.isEmpty()) ? Integer.parseInt(distance) * 1312 : 0;
        } catch (NumberFormatException e) {
            Log.e("WorkoutRoutine", "Invalid distance value: " + distance, e);
            return 0;
        }
    }

    public int calculateWeights() {
        try {
            int repsInt = (reps != null && !reps.isEmpty()) ? Integer.parseInt(reps) : 0;
            int setsInt = (sets != null && !sets.isEmpty()) ? Integer.parseInt(sets) : 0;
            return repsInt * setsInt;
        } catch (NumberFormatException e) {
            Log.e("WorkoutRoutine", "Invalid reps/sets value: reps=" + reps + ", sets=" + sets, e);
            return 0;
        }
    }

    public int calculateCalories(int steps, int weights) {
        return (int) ((steps * 0.05) + (weights * 1.5));
    }




}