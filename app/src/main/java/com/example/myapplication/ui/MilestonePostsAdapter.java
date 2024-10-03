package com.example.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.example.myapplication.PostDetailActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the Posts data.
 */
public class MilestonePostsAdapter extends RecyclerView.Adapter<MilestonePostsAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<Post> miPostsData;
    private Context miContext;

    /**
     * Constructor that passes in the Posts data and the context.
     *
     * @param postsData ArrayList containing the Posts data.
     * @param context Context of the application.
     */
    public MilestonePostsAdapter(Context context, ArrayList<Post> postsData) {
        this.miPostsData = postsData;
        this.miContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public MilestonePostsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(miContext).
                inflate(R.layout.milestone_list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(MilestonePostsAdapter.ViewHolder holder,
                                 int position) {
        // Get current Post.
        Post currentPost = miPostsData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentPost);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return miPostsData.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView miTitleText;
        private TextView miInfoText;
        private ImageView miPostsImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the milestone_list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            miTitleText = itemView.findViewById(R.id.workoutTitle);
            miInfoText = itemView.findViewById(R.id.subTitle);
            miPostsImage = itemView.findViewById(R.id.milestonePostsImage);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Post currentPost){
            // Populate the textviews with data.
            miTitleText.setText(currentPost.getTitle());
            miInfoText.setText(currentPost.getInfo());
            Glide.with(miContext).load(currentPost.getImageResource()).into(miPostsImage);
        }




        @Override
        public void onClick(View view) {
            Post currentPost = miPostsData.get(getAdapterPosition());

//            Intent detailIntent = new Intent(mContext, PostDetailActivity.class);
//            detailIntent.putExtra("title", currentPost.getTitle());
//            detailIntent.putExtra("image_resource",
//                    currentPost.getImageResource());
//            mContext.startActivity(detailIntent);

        }
    }
}
