package com.example.myapplication.ui.progress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

// Updates Observer
interface Observer {
    void update();
}

// Manage Observer
interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

public class ProgressViewModel extends ViewModel implements Subject {

    private final MutableLiveData<String> mText;
    private List<Observer> observers;

    public ProgressViewModel() {
        mText = new MutableLiveData<>();
        observers = new ArrayList<>(); // List of Observers
        // mText.setValue("This is notifications fragment");
    }

    // LiveData Getter
    public LiveData<String> getText() {
        return mText;
    }


    @Override // Add Observer
    public void registerObserver(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override //Remove Observer
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override //Notify All Observers of Change
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    // Notify Observers When Text Change
    public void updateText(String text) {
        mText.setValue(text);
        notifyObservers();
    }
}
