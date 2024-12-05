package com.example.myapplication;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
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

        // Create "Step X: " with underlined formatting and set font size to 20sp
        String workoutTitlePrefix = "Step " + (position + 1) + ": ";
        String fullTitle = workoutTitlePrefix + (routine.getWorkoutRoutineTitle() != null ? routine.getWorkoutRoutineTitle() : "");

        SpannableString spannableString = new SpannableString(fullTitle);

        // Underline the "Step X: "
        spannableString.setSpan(new UnderlineSpan(), 0, workoutTitlePrefix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set font size for "Step X: " to 20sp
        spannableString.setSpan(new RelativeSizeSpan(1.4f), 0, workoutTitlePrefix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Adjust the multiplier accordingly to achieve 20sp equivalent size

        // Alternatively, use TextAppearanceSpan if you have a style defined with 20sp
        // TextAppearanceSpan textSizeSpan = new TextAppearanceSpan(context, R.style.TextAppearance_StepTitle);
        // spannableString.setSpan(textSizeSpan, 0, workoutTitlePrefix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.titleTextView.setText(spannableString);

        holder.workoutNameTextView.setText(routine.getWorkoutName());

        // Dynamically hide or show attributes if they are empty or null, with prefixed text and units where necessary
        toggleVisibility(holder.repsTextView, "Reps: ", routine.getReps());
        toggleVisibility(holder.setsTextView, "Sets: ", routine.getSets());
        toggleVisibility(holder.weightTextView, "Weight: ", routine.getWeight(), " lbs");
        toggleVisibility(holder.distanceTextView, "Distance: ", routine.getDistance(), " km");
        toggleVisibility(holder.durationTextView, "Duration: ", routine.getDuration(), " min");
    }

    // Overloaded method to handle attributes with units
    private void toggleVisibility(TextView textView, String prefix, String value) {
        if (value == null || value.isEmpty()) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(prefix + value);
        }
    }

    private void toggleVisibility(TextView textView, String prefix, String value, String unit) {
        if (value == null || value.isEmpty()) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(prefix + value + unit);
        }
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
