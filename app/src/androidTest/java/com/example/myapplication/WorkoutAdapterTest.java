package com.example.myapplication;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class WorkoutAdapterTest {

    private WorkoutAdapter workoutAdapter;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        workoutAdapter = new WorkoutAdapter(new ArrayList<>());
    }

    // Tests updating the workout list
    @Test
    public void updateWorkoutList() {
        Workout workout1 = new Workout();
        workout1.setWorkoutName("Cardio Day");
        Workout workout2 = new Workout();
        workout2.setWorkoutName("Strength Day");

        workoutAdapter.updateWorkoutList(Arrays.asList(workout1, workout2));
        assertEquals("Workout list should have 2 items after update", 2, workoutAdapter.getItemCount());
    }

    // Tests ViewHolder creation
    @Test
    public void onCreateViewHolder() {
        ViewGroup mockParent = new FrameLayout(context);
        int viewType = 0;

        WorkoutAdapter.WorkoutViewHolder viewHolder = workoutAdapter.onCreateViewHolder(mockParent, viewType);
        assertNotNull("ViewHolder should not be null", viewHolder);
    }

    // Tests binding a ViewHolder with data
    @Test
    public void onBindViewHolder() {
        Workout workout = new Workout();
        workout.setWorkoutName("Leg Day");
        workoutAdapter.updateWorkoutList(Arrays.asList(workout));

        ViewGroup mockParent = new FrameLayout(context);
        WorkoutAdapter.WorkoutViewHolder viewHolder = workoutAdapter.onCreateViewHolder(mockParent, 0);
        workoutAdapter.onBindViewHolder(viewHolder, 0);

        assertEquals("ViewHolder's TextView should display the workout name", "Leg Day", viewHolder.workoutNameTextView.getText().toString());
    }
}