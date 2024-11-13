package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.util.Log;


public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private List<Workout> workoutList;

    public WorkoutAdapter(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public void updateWorkoutList(List<Workout> newWorkoutList) {
        workoutList.clear();
        workoutList.addAll(newWorkoutList);
        Log.i("WorkoutAdapter", "Workout list updated. Item count: " + workoutList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.workoutNameTextView.setText(workout.getWorkoutName());

        boolean hasWeightliftingDetails = workout.getReps() != null || workout.getSets() != null || workout.getWeight() != null;
        boolean hasCardioDetails = workout.getDistance() != null || workout.getDuration() != null;

        if (hasWeightliftingDetails) {
            holder.repsTextView.setVisibility(View.VISIBLE);
            holder.setsTextView.setVisibility(View.VISIBLE);
            holder.weightTextView.setVisibility(View.VISIBLE);

            holder.repsTextView.setText("Reps: " + (workout.getReps() != null ? workout.getReps() : "N/A"));
            holder.setsTextView.setText("Sets: " + (workout.getSets() != null ? workout.getSets() : "N/A"));
            holder.weightTextView.setText("Weight: " + (workout.getWeight() != null ? workout.getWeight() + " kg" : "N/A"));
        } else {
            holder.repsTextView.setVisibility(View.GONE);
            holder.setsTextView.setVisibility(View.GONE);
            holder.weightTextView.setVisibility(View.GONE);
        }

        if (hasCardioDetails) {
            holder.distanceTextView.setVisibility(View.VISIBLE);
            holder.durationTextView.setVisibility(View.VISIBLE);

            holder.distanceTextView.setText("Distance: " + (workout.getDistance() != null ? workout.getDistance() + " km" : "N/A"));
            holder.durationTextView.setText("Duration: " + (workout.getDuration() != null ? workout.getDuration() + " min" : "N/A"));
        } else {
            holder.distanceTextView.setVisibility(View.GONE);
            holder.durationTextView.setVisibility(View.GONE);
        }

        if (!hasWeightliftingDetails && !hasCardioDetails) {
            holder.noDetailsTextView.setVisibility(View.VISIBLE);
            holder.noDetailsTextView.setText("No details available");
        } else {
            holder.noDetailsTextView.setVisibility(View.GONE);
        }

        Log.i("WorkoutAdapter", "Bound workout: " + workout.getWorkoutName());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView workoutNameTextView;
        TextView repsTextView;
        TextView setsTextView;
        TextView weightTextView;
        TextView distanceTextView;
        TextView durationTextView;
        TextView noDetailsTextView; // Add this line

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutNameTextView = itemView.findViewById(R.id.workoutNameTextView);
            repsTextView = itemView.findViewById(R.id.repsTextView);
            setsTextView = itemView.findViewById(R.id.setsTextView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            distanceTextView = itemView.findViewById(R.id.distanceTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            noDetailsTextView = itemView.findViewById(R.id.noDetailsTextView); // Initialize it here
        }
    }
}


