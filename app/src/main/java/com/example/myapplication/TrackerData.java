package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

//observer tracker for steps, calories and weight. will change all other data at the same time

public class TrackerData {
    private int steps;
    private int calories;
    private int weight;

    private final List<TrackerObserver> observers = new ArrayList<>();

    public void addObserver(TrackerObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TrackerObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (TrackerObserver observer : observers) {
            observer.update(steps, calories, weight);
        }
    }

    public void setSteps(int steps) {
        this.steps = steps;
        notifyObservers();
    }

    public void setCalories(int calories) {
        this.calories = calories;
        notifyObservers();
    }

    public void setWeight(int weight) {
        this.weight = weight;
        notifyObservers();
    }
}
