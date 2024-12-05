package com.example.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.PostDetailActivity;
import com.example.myapplication.R;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<String> mRoutineTitles;
    private Context mContext;

    public PostsAdapter(Context context, List<String> routineTitles) {
        this.mRoutineTitles = routineTitles;
        this.mContext = context;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder, int position) {
        // Get current workout routine title.
        String currentTitle = mRoutineTitles.get(position);

        // Populate the TextView with the workout routine title.
        holder.bindTo(currentTitle);
    }

    @Override
    public int getItemCount() {
        return mRoutineTitles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleText;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the TextView.
            mTitleText = itemView.findViewById(R.id.workoutRoutineTitle);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(String routineTitle) {
            // Populate the TextView with the workout routine title.
            mTitleText.setText(routineTitle);
        }

        @Override
        public void onClick(View view) {
            String currentTitle = mRoutineTitles.get(getAdapterPosition());

            Intent detailIntent = new Intent(mContext, PostDetailActivity.class);
            detailIntent.putExtra("title", currentTitle);
            mContext.startActivity(detailIntent);
        }
    }
}
