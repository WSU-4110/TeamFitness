package com.example.myapplication.progress;

import com.example.myapplication.ui.progress.ProgressViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Workout;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;

public class ProgressViewModelTest {

    private ProgressViewModel progressViewModel;

    @Before
    public void setUp() {
        progressViewModel = new ProgressViewModel();
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override
            public void executeOnDiskIO(Runnable runnable) { runnable.run(); }
            @Override
            public void postToMainThread(Runnable runnable) { runnable.run(); }
            @Override
            public boolean isMainThread() { return true; }
        });
    }

    // Tests getting workout list
    @Test
    public void getWorkoutList() {
        List<Workout> mockWorkouts = new ArrayList<>();
        Workout workout = new Workout();
        workout.setWorkoutName("Test Workout");
        mockWorkouts.add(workout);

        MutableLiveData<List<Workout>> liveData = new MutableLiveData<>();
        liveData.postValue(mockWorkouts);

        progressViewModel.getWorkoutList("testUserId").observeForever(workouts -> {
            assertNotNull("Workout list should not be null", workouts);
            assertEquals("Workout list size should be 1", 1, workouts.size());
            assertEquals("Workout name should be 'Test Workout'", "Test Workout", workouts.get(0).getWorkoutName());
        });
    }
}






