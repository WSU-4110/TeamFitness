package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WorkoutRoutineAdapter extends RecyclerView.Adapter<WorkoutRoutineAdapter.WorkoutViewHolder> {
    private final List<WorkoutRoutine> workoutRoutines;
    private final Context context;

    public WorkoutRoutineAdapter(Context context, List<WorkoutRoutine> workoutRoutines) {
        this.context = context;
        this.workoutRoutines = workoutRoutines;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_routine_list, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        WorkoutRoutine routine = workoutRoutines.get(position);
        holder.titleTextView.setText(routine.getWorkoutRoutineTitle());
        holder.workoutNameTextView.setText(routine.getWorkoutName());
        holder.repsTextView.setText(routine.getReps());
        holder.setsTextView.setText(routine.getSets());
        holder.weightTextView.setText(routine.getWeight());
        holder.distanceTextView.setText(routine.getDistance());
        holder.durationTextView.setText(routine.getDuration());
    }

    @Override
    public int getItemCount() {
        return workoutRoutines.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView workoutNameTextView;
        TextView repsTextView;
        TextView setsTextView;
        TextView weightTextView;
        TextView distanceTextView;
        TextView durationTextView;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.routine_title);
            workoutNameTextView = itemView.findViewById(R.id.workout_name);
            repsTextView = itemView.findViewById(R.id.reps);
            setsTextView = itemView.findViewById(R.id.sets);
            weightTextView = itemView.findViewById(R.id.weight);
            distanceTextView = itemView.findViewById(R.id.distance);
            durationTextView = itemView.findViewById(R.id.duration);
        }
    }
}
