package com.example.myapplication;

import java.io.Serializable;

public class Workout {
    private String workoutName;
    private String reps;
    private String sets;
    private String weight;
    private String distance;
    private String duration;

    public Workout() {}

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
}

