package com.example.myapplication.ui.tracker;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;

import androidx.lifecycle.LiveData;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

public class TrackerViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private TrackerViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TrackerViewModel();
        viewModel.setText("This is home fragment");
    }

    @Test
    public void getText_returnsCorrectInitialValue() {
        LiveData<String> liveData = viewModel.getText();
        liveData.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                assertEquals("Expected initial text does not match", "This is home fragment", s);
            }
        });
    }
}