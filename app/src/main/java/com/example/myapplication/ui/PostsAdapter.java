package com.example.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the Posts data.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<Post> mPostsData;
    private Context mContext;

    /**
     * Constructor that passes in the Posts data and the context.
     *
     * @param postsData ArrayList containing the Posts data.
     * @param context Context of the application.
     */
    public PostsAdapter(Context context, ArrayList<Post> postsData) {
        this.mPostsData = postsData;
        this.mContext = context;
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
    public PostsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder holder,
                                 int position) {
        // Get current Post.
        Post currentPost = mPostsData.get(position);

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
        return mPostsData.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mPostsImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mPostsImage = itemView.findViewById(R.id.postsImage);
        }

        void bindTo(Post currentPost){
            // Populate the textviews with data.
            mTitleText.setText(currentPost.getTitle());
            mInfoText.setText(currentPost.getInfo());


            Glide.with(mContext).load(currentPost.getImageResource()).into(mPostsImage);
        }
    }
}
