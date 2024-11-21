package com.example.myapplication.ui.tracker;

import com.example.myapplication.R;

import static org.junit.Assert.assertEquals;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import android.view.View;
import android.widget.ProgressBar;

import androidx.test.core.app.ApplicationProvider;
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.annotation.Config;

@Config(
        sdk = {28},
        manifest = Config.NONE
)
@RunWith(AndroidJUnit4.class)
public class TrackerFragmentTest {

    private TrackerFragment trackerFragment;

    private ProgressBar mockProgressSteps;
    private ProgressBar mockProgressCalories;
    private ProgressBar mockProgressWeight;
    private DonutProgress spyCircularProgress;

    @Before
    public void setUp() {

        trackerFragment = new TrackerFragment();


        // Mock ProgressBars
        mockProgressSteps = mock(ProgressBar.class);
        mockProgressCalories = mock(ProgressBar.class);
        mockProgressWeight = mock(ProgressBar.class);

        // Spy DonutProgress
        spyCircularProgress = spy(new DonutProgress(ApplicationProvider.getApplicationContext()));

        // Mock view
        View mockView = mock(View.class);
        when(mockView.findViewById(R.id.progress_steps)).thenReturn(mockProgressSteps);
        when(mockView.findViewById(R.id.progress_calories)).thenReturn(mockProgressCalories);
        when(mockView.findViewById(R.id.progress_weight)).thenReturn(mockProgressWeight);
        when(mockView.findViewById(R.id.circular_progress)).thenReturn(spyCircularProgress);

        trackerFragment.progressSteps = mockProgressSteps;
        trackerFragment.progressCalories = mockProgressCalories;
        trackerFragment.progressWeight = mockProgressWeight;
        trackerFragment.circularProgress = spyCircularProgress;
    }

    @Test
    public void testUpdatePieChart() {
        // Mock  values
        when(mockProgressSteps.getProgress()).thenReturn(3000);
        when(mockProgressSteps.getMax()).thenReturn(10000);

        when(mockProgressCalories.getProgress()).thenReturn(200);
        when(mockProgressCalories.getMax()).thenReturn(500);

        when(mockProgressWeight.getProgress()).thenReturn(30);
        when(mockProgressWeight.getMax()).thenReturn(100);

        trackerFragment.updatePieChart();

        // Verify values
        int totalProgress = 3000 + 200 + 30;
        int totalMax = 10000 + 500 + 100;
        float expectedPercentage = (totalProgress / (float) totalMax) * 100;

        assertEquals(100, spyCircularProgress.getMax());
        assertEquals((int) expectedPercentage, spyCircularProgress.getProgress());
    }

}
