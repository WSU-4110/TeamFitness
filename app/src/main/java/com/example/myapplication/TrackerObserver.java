package com.example.myapplication;

//sends updates to update class in homeActivity
public interface TrackerObserver {
    void update(int steps, int calories, int weight);
}
